package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;

public class Terrain extends DrawableComponentBase {
    public Terrain(GameBase game) {
        super(game);
    }

    private CameraInputController camController;
    private TerrainChunk chunk;
    private Mesh mesh;
    private ShaderProgram shader;
    private Texture texture;
    private final Matrix3 normalMatrix = new Matrix3();

    private static final float[] lightPosition = { 5, 35, 5 };
    private static final float[] ambientColor = { 0.2f, 0.2f, 0.2f, 1.0f };
    private static final float[] diffuseColor = { 0.5f, 0.5f, 0.5f, 1.0f };
    private static final float[] specularColor = { 0.7f, 0.7f, 0.7f, 1.0f };

    private Matrix4 model = new Matrix4();
    private Matrix4 modelView = new Matrix4();

    private final int vertexSize = 3 + 3 + 1 + 2;

    @Override
    public void initialize() {
        super.initialize();

        this.texture = new Texture(Gdx.files.internal("grass.jpg"));

        Pixmap pixmap = new Pixmap(Gdx.files.internal("height_map.png"));

        this.chunk = new TerrainChunk(pixmap, this.vertexSize);
        this.chunk.printVertices();
        this.chunk.printIndices();

        this.mesh = new Mesh(true,
                             this.chunk.vertices.length / 3,
                             this.chunk.indices.length,
                             new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                             new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE),
                             new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
                             new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE));

        this.mesh.setVertices(this.chunk.vertices);
        this.mesh.setIndices(this.chunk.indices);

        this.camController = new CameraInputController(this.getGame().getActiveCamera().getInnerCamera());
        Gdx.input.setInputProcessor(this.camController);

        ShaderProgram.pedantic = false;

        this.shader = new ShaderProgram(Gdx.files.internal("shaders/terrain.vert"),
                                        Gdx.files.internal("shaders/terrain.frag"));
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void render() {
        super.render();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        this.camController.update();

        // This is wrong?
        this.model.setToRotation(new Vector3(0f, 1f, 0f), 45f);
        this.modelView.set(this.getGame().getActiveCamera().getInnerCamera().view).mul(this.model);

        this.texture.bind();

        this.shader.begin();
        this.shader.setUniformMatrix("u_MVPMatrix", this.getGame().getActiveCamera().getViewProjection());
        this.shader.setUniformMatrix("u_normalMatrix", this.normalMatrix.set(this.modelView).inv().transpose());

        this.shader.setUniform3fv("u_lightPosition", lightPosition, 0, 3);
        this.shader.setUniform4fv("u_ambientColor", ambientColor, 0, 4);
        this.shader.setUniform4fv("u_diffuseColor", diffuseColor, 0, 4);
        this.shader.setUniform4fv("u_specularColor", specularColor, 0, 4);

        this.shader.setUniformi("u_texture", 0);

        this.mesh.render(this.shader, GL20.GL_TRIANGLES);

        this.shader.end();
    }

    final static class TerrainChunk {
        public float[] heightMap;
        public float[] vertices;
        public short[] indices;

        public final int vertexSize;

        private final Pixmap pixmap;

        public TerrainChunk(Pixmap pixmap, int vertexSize) {
            if (pixmap.getWidth() * pixmap.getHeight() > Short.MAX_VALUE) {
                throw new IllegalArgumentException("Chunk size too big, width*height must be <= " + Short.MAX_VALUE);
            }

            this.vertexSize = vertexSize;
            this.pixmap = pixmap;

            this.buildHeightMap();
            this.buildVertices(40f, 0.25f);
            this.buildIndices();
            this.buildNormals();
        }

        private short getWidth() {
            return (short) this.pixmap.getWidth();
        }

        private short getHeight() {
            return (short) this.pixmap.getHeight();
        }

        public void buildHeightMap() {
            this.heightMap = new float[this.pixmap.getWidth() * this.pixmap.getHeight()];

            Color color = new Color();

            for (int x = 0; x < this.getWidth(); x++) {
                for (int y = 0; y < this.getHeight(); y++) {
                    Color.rgba8888ToColor(color, this.pixmap.getPixel(x, y));
                    this.heightMap[(this.getWidth() * x) + y] = color.r;
                }
            }
        }

        // strength: multiplier for height map
        public void buildVertices(float strength, float scale) {
            this.vertices = new float[this.heightMap.length * this.vertexSize];

            for (int z = 0; z < this.getHeight(); z++) {
                for (int x = 0; x < this.getWidth(); x++) {
                    // POSITION
                    int base = this.vertexSize * (this.getHeight() * z + x);
                    this.vertices[base + 0] = scale * x;
                    this.vertices[base + 1] = this.heightMap[z * this.getHeight() + x] * strength;
                    this.vertices[base + 2] = scale * z * -1f; // negating "z" in order to build our terrain in a forward direction.

                    // NORMAL, skip these for now

                    // COLOR
                    this.vertices[base + 6] = Color.WHITE.toFloatBits();

                    // TEXTURE
                    this.vertices[base + 7] = (x / (float) this.getWidth());  // u
                    this.vertices[base + 8] = (z / (float) this.getHeight()); // v
                }
            }
        }

        private int getTrianglesCount(int width, int height) {
            return ((width  * 2) - 2) * (height - 1);
        }

        private int getIndicesCount(int trianglesCount) {
            return 3 * trianglesCount;
        }

        private void buildNormals() {
            int trianglesCount = this.getTrianglesCount(this.getWidth(), this.getHeight());

            for (int i = 0; i < trianglesCount; i++) {
                final short a = this.indices[i * 3 + 0];
                final short b = this.indices[i * 3 + 1];
                final short c = this.indices[i * 3 + 2];

                final float x1 = this.vertices[a * this.vertexSize + 0];
                final float y1 = this.vertices[a * this.vertexSize + 1];
                final float z1 = this.vertices[a * this.vertexSize + 2];

                final float x2 = this.vertices[b * this.vertexSize + 0];
                final float y2 = this.vertices[b * this.vertexSize + 1];
                final float z2 = this.vertices[b * this.vertexSize + 2];

                final float x3 = this.vertices[c * this.vertexSize + 0];
                final float y3 = this.vertices[c * this.vertexSize + 1];
                final float z3 = this.vertices[c * this.vertexSize + 2];

                final Vector3 v1 = new Vector3(x2 - x1, y2 - y1, z2 - z1).nor();
                final Vector3 v2 = new Vector3(x3 - x1, y3 - y1, z3 - z1).nor();

                final Vector3 normal = v1.crs(v2);

                this.vertices[a * this.vertexSize + 3] = normal.x;
                this.vertices[a * this.vertexSize + 4] = normal.y;
                this.vertices[a * this.vertexSize + 5] = normal.z;

                this.vertices[b * this.vertexSize + 3] = normal.x;
                this.vertices[b * this.vertexSize + 4] = normal.y;
                this.vertices[b * this.vertexSize + 5] = normal.z;

                this.vertices[c * this.vertexSize + 3] = normal.x;
                this.vertices[c * this.vertexSize + 4] = normal.y;
                this.vertices[c * this.vertexSize + 5] = normal.z;
            }
        }

        private void buildIndices() {
            final int trianglesCount = this.getTrianglesCount(this.getWidth(), this.getHeight());
            this.indices = new short[this.getIndicesCount(trianglesCount)];

            for (int z = 0; z < this.getHeight() - 1; z++) {
                for (int x = 0; x < this.getHeight() - 1; x++) {
                    int base = (z * (this.getHeight() - 1) * 6) + (x * 6);

                    final short a = (short) ((this.getHeight() * z) + x);
                    final short b = (short) ((this.getHeight() * z) + x + 1);
                    final short c = (short) ((this.getHeight() * (z + 1)) + x + 1);

                    this.indices[base + 0] = a;
                    this.indices[base + 1] = b;
                    this.indices[base + 2] = c;

                    final short d = (short) ((this.getHeight() * (z + 1)) + x + 1);
                    final short e = (short) ((this.getHeight() * (z + 1)) + x);
                    final short f = (short) ((this.getHeight() * z) + x);

                    this.indices[base + 3] = d;
                    this.indices[base + 4] = e;
                    this.indices[base + 5] = f;
                }
            }
        }

        private void printVertices() {
            int positions = this.vertices.length / this.vertexSize;
            System.out.println("printing " + positions + " vertices.");

            for (int i = 0; i < positions; i++) {
                int base = i * this.vertexSize;
                System.out.println(this.vertices[base + 0] + "," +
                                   this.vertices[base + 1] + "," +
                                   this.vertices[base + 2]);
            }
        }

        private void printIndices() {
            int positions = this.indices.length / 3;
            System.out.println("printing " + this.indices.length + " indices.");

            for (int i = 0; i < positions; i++) {
                int base = i * 3;
                System.out.println(this.indices[base + 0] + "," +
                                   this.indices[base + 1] + "," +
                                   this.indices[base + 2]);
            }
        }
    }
}