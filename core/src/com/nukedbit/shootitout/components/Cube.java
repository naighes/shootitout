package com.nukedbit.shootitout.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.graphics.ViewPort;

public class Cube extends DrawableComponentBase {
    protected Cube(GameBase game) {
        super(game);

        this.position = new Vector3(-1.3f, 2.5f, -4f);
    }

    private Camera camera;
    private Model model;
    private ModelInstance instance;
    private ModelBatch batch;
    private Environment environment;
    private Vector3 position;
    private float rotation = 0.0f;
    private final float scale = 0.1f;
    private Matrix4 transform;

    @Override
    public void initialize() {
        super.initialize();

        this.batch = new ModelBatch();

        this.buildEnvironment();
        this.buildCamera(this.getGame().getViewPort());

        ModelBuilder builder = new ModelBuilder();
        this.model = builder.createBox(0.5f, 0.5f, 0.5f,
                                       new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                                       VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        this.instance = new ModelInstance(this.model);
        this.transform = this.instance.transform.cpy();
    }

    private void buildEnvironment() {
        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    private void buildCamera(ViewPort viewPort) {
        this.camera = new PerspectiveCamera(67, viewPort.getWidth(), viewPort.getHeight());
        this.camera.position.set(0, 0f, 0);
        this.camera.lookAt(0f, 0f, -1f);
        this.camera.near = 0.1f;
        this.camera.far = 100f;
        this.camera.update();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.rotation += 1.2f;
        this.position.y -= 0.005f;

        Matrix4 localTransform = new Matrix4();
        localTransform.mul(this.transform);
        localTransform.scale(this.scale, this.scale, this.scale);
        localTransform.mul(new Matrix4().setToTranslation(this.position))
                      .rotate(new Vector3(1f, 0f, 0f), this.rotation)
                      .rotate(new Vector3(0f, 1f, 0f), 45f);

        this.instance.transform = localTransform;
    }

    @Override
    public void render() {
        super.render();

        this.batch.begin(this.camera);
        this.batch.render(this.instance, this.environment);
        this.batch.end();
    }
}