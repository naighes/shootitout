package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.physics.WorldObject;

public class Bullet extends DrawableComponentBase implements WorldObject {
    private Vector3 position;
    private final float scale;
    private Matrix4 transform;
    private Texture texture;

    public Bullet(GameBase game,
                  Vector3 position,
                  float scale) {
        super(game);

        this.position = position;
        this.scale = scale;
    }

    private ModelInstance instance;
    private ModelBatch batch;

    @Override
    public void initialize() {
        super.initialize();

        this.texture = new Texture(Gdx.files.internal("bullet_3.png"));
        this.batch = new ModelBatch();
        ModelBuilder builder = new ModelBuilder();
        this.instance = new ModelInstance(buildRectangle(builder));
        this.transform = this.instance.transform.cpy();
    }

    private Model buildRectangle(ModelBuilder builder) {
        TextureAttribute textureAttribute = TextureAttribute.createDiffuse(this.texture);
        BlendingAttribute blendingAttribute = new BlendingAttribute(GL20.GL_ONE, GL20.GL_ONE);
        Model rectangle = builder.createRect(0f, 0f, 0f,
                                             1f, 0f, 0f,
                                             1f, 6f, 0f,
                                             0f, 6f, 0f,
                                             0f, 0f, 1f,
                                             new Material(textureAttribute, blendingAttribute),
                                             VertexAttributes.Usage.Position |
                                             VertexAttributes.Usage.Normal |
                                             VertexAttributes.Usage.TextureCoordinates);
        return rectangle;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.removeOnceOutsideFrustum();

        this.position.y += 0.05f;

        Matrix4 localTransform = new Matrix4();

        localTransform.mul(this.transform);
        localTransform.mul(new Matrix4().setToTranslation(this.position));
        localTransform.mul(new Matrix4().scale(scale, scale, scale));

        this.instance.transform = localTransform;
    }

    private void removeOnceOutsideFrustum() {
        Frustum frustum = this.getGame().getActiveCamera().getInnerCamera().frustum;

        if (!frustum.pointInFrustum(this.position))
            this.getGame().getComponents().remove(this);
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