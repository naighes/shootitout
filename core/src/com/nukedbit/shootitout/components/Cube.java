package com.nukedbit.shootitout.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.physics.RigidBody;

public class Cube extends DrawableComponentBase {
    private final Matrix4 initialRotation;
    private Vector3 position;
    private final float scale;
    private Matrix4 transform;

    private final RigidBody body;

    protected Cube(GameBase game,
                   Matrix4 initialRotation,
                   Vector3 position,
                   float scale,
                   RigidBody body) {
        super(game);

        this.initialRotation = initialRotation;
        this.position = position;
        this.scale = scale;
        this.body = body;
    }

    private ModelInstance instance;
    private ModelBatch batch;
    private float rotation = 0.0f;

    @Override
    public void initialize() {
        super.initialize();

        this.batch = new ModelBatch();
        ModelBuilder builder = new ModelBuilder();
        this.instance = new ModelInstance(this.buildCube(builder));
        this.transform = this.instance.transform.cpy();
    }

    private Model buildCube(ModelBuilder builder) {
        return builder.createBox(0.5f, 0.5f, 0.5f,
                                 new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                                 VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.body.update(dt);

        this.rotation += 1.2f;
        this.position.add(this.body.getVelocity());

        Matrix4 localTransform = new Matrix4();

        localTransform.mul(this.transform);
        localTransform.mul(new Matrix4().setToTranslation(this.position));
        localTransform.mul(new Matrix4().scale(scale, scale, scale));
        localTransform.mul(new Matrix4().rotate(new Vector3(1f, 0f, 0f), this.rotation));
        localTransform.mul(this.initialRotation);

        this.instance.transform = localTransform;
    }

    @Override
    public void render() {
        super.render();

        this.batch.begin(this.getGame().getActiveCamera().getInnerCamera());
        this.batch.render(this.instance, this.getGame().getEnvironment().getInnerEnvironment());
        this.batch.end();
    }
}