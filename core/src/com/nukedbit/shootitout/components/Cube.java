package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
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

public class Cube extends DrawableComponentBase {
    protected Cube(GameBase game) {
        super(game);

        this.position = new Vector3(0f, 0f, 0f);
    }

    private Camera camera;
    private Model model;
    private ModelInstance instance;
    private ModelBatch batch;
    private Environment environment;
    private Vector3 position;
    private float rotation = 0.0f;
    private final float scale = 0.1f;

    @Override
    public void initialize() {
        super.initialize();

        this.batch = new ModelBatch();

        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        this.camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(0, 0f, -1f);
        this.camera.lookAt(0f, 0f, 0f);
        this.camera.near = 0.1f;
        this.camera.far = 300f;
        this.camera.update();

        ModelBuilder builder = new ModelBuilder();
        this.model = builder.createBox(0.5f, 0.5f, 0.5f,
                                       new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                                       VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        this.instance = new ModelInstance(this.model);

        this.position.add(new Vector3(0.3f, 0.3f, 0f));
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.rotation += 1.2f;
        this.position.y -= 0.002f;

        Matrix4 localTransform = this.instance.transform.cpy();

        Matrix4 rotation = localTransform.setToRotation(new Vector3(0f, 1f, 0f), 30f);
        localTransform.mul(rotation);
        localTransform.mul(new Matrix4().setToTranslation(this.position));

        localTransform//.setToTranslation(this.position)
                      .scale(this.scale, this.scale, this.scale)
                      .rotate(new Vector3(1f, 0f, 0f), this.rotation);

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