package com.nukedbit.shootitout.components;

import com.badlogic.gdx.graphics.Color;
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
    private Vector3 position;
    private Matrix4 transform;

    protected Cube(GameBase game) {
        super(game);

        this.position = new Vector3(-0.5f, 0.7f, 0f);
    }

    private ModelInstance instance;
    private ModelBatch batch;
    private Environment environment;
    private float rotation = 0.0f;
    private final float scale = 0.11f;

    @Override
    public void initialize() {
        super.initialize();

        this.batch = new ModelBatch();
        this.buildEnvironment();

        ModelBuilder builder = new ModelBuilder();
        this.instance = new ModelInstance(this.buildCube(builder));
        this.transform = this.instance.transform.cpy();
    }

    private Model buildCube(ModelBuilder builder) {
        return builder.createBox(0.5f, 0.5f, 0.5f,
                                 new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                                 VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    private void buildEnvironment() {
        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.rotation += 1.2f;
        this.position.y -= 0.003f;

        Matrix4 localTransform = new Matrix4();

        localTransform.mul(this.transform);
        localTransform.mul(new Matrix4().setToTranslation(this.position));
        localTransform.mul(new Matrix4().scale(scale, scale, scale));
        localTransform.mul(new Matrix4().rotate(new Vector3(1f, 0f, 0f), this.rotation)
                .rotate(new Vector3(0f, 1f, 0f), 45f));

        this.instance.transform = localTransform;
    }

    @Override
    public void render() {
        super.render();

        this.batch.begin(this.getGame().getActiveCamera().getInnerCamera());
        this.batch.render(this.instance, this.environment);
        this.batch.end();
    }
}