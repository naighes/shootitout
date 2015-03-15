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
import com.nukedbit.framework.physics.WorldObject;

public class Bullet extends DrawableComponentBase implements WorldObject {
    private Vector3 position;
    private Matrix4 transform;

    public Bullet(GameBase game, Vector3 initialPosition) {
        super(game);

        this.position = initialPosition;
    }

    private ModelInstance instance;
    private ModelBatch batch;

    @Override
    public void initialize() {
        super.initialize();

        this.batch = new ModelBatch();

        ModelBuilder builder = new ModelBuilder();
        this.instance = new ModelInstance(buildRectangle(builder));
        this.transform = this.instance.transform.cpy();
    }

    private Model buildRectangle(ModelBuilder builder) {
        return builder.createRect(0f, 0f, 0f,
                                  0.05f, 0f, 0f,
                                  0.05f, 0.2f, 0f,
                                  0f, 0.2f, 0f,
                                  0f, 0f, 1f,
                                  new Material(ColorAttribute.createDiffuse(Color.RED)),
                                  VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.position.y += 0.2f;

        Matrix4 localTransform = new Matrix4();

        localTransform.mul(this.transform);
        localTransform.mul(new Matrix4().setToTranslation(this.position));

        this.instance.transform = localTransform;
    }

    @Override
    public void render() {
        super.render();

        this.batch.begin(this.getGame().getActiveCamera().getInnerCamera());
        this.batch.render(this.instance, this.getGame().getEnvironment().getInnerEnvironment());
        this.batch.end();
    }

    @Override
    public Vector3 getPosition() {
        return this.position;
    }
}