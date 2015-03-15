package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;

public class Enemy extends DrawableComponentBase {
    private Vector3 position;
    private Vector3 direction;
    private final Matrix4 initialRotation;
    private Matrix4 transform;

    private final float scale;
    public Environment environment;
    public ModelBatch modelBatch;
    public ModelInstance instance;

    protected Enemy(GameBase game) {
        super(game);

        // positive z is outside screen.
        this.position = new Vector3(0f, 0f, -4f);
        this.direction = new Vector3(0f, 1f, 0f);
        this.initialRotation = new Matrix4().rotate(new Vector3(0f, 1f, 0f), 180f)
                                            .rotate(new Vector3(1f, 0f, 0f), -90f);
        this.scale = 0.5f;
    }

    @Override
    public void initialize() {
        super.initialize();

        this.modelBatch = new ModelBatch();
        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        ModelLoader<?> loader = new ObjLoader();
        this.instance = new ModelInstance(loader.loadModel(Gdx.files.internal("ship/ship.obj")));
        this.transform = this.instance.transform.cpy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        Matrix4 localTransform = new Matrix4();

        localTransform.mul(this.transform);
        localTransform.mul(new Matrix4().setToTranslation(this.position));
        localTransform.mul(new Matrix4().scale(scale, scale, scale));
        localTransform.mul(initialRotation);

        this.instance.transform = localTransform;
    }

    @Override
    public void render() {
        super.render();

        this.modelBatch.begin(this.getGame().getActiveCamera().getInnerCamera());
        this.modelBatch.render(this.instance, this.environment);
        this.modelBatch.end();
    }
}