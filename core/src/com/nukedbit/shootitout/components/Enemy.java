package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
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
    public Environment environment;
    public PerspectiveCamera camera;
    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;
    private Matrix4 originalTransform;

    protected Enemy(GameBase game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        this.modelBatch = new ModelBatch();
        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        this.camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(0f, 0f, 1f);
        this.camera.lookAt(0, 0, 0);
        this.camera.near = 0f;
        this.camera.far = 300f;
        this.camera.update();

        ModelLoader<?> loader = new ObjLoader();
        this.model = loader.loadModel(Gdx.files.internal("ship/ship.obj"));
        this.instance = new ModelInstance(model);
        this.originalTransform = this.instance.transform;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        Matrix4 world = new Matrix4().idt();
        world.mul(this.originalTransform);
        world.scale(0.17f, 0.17f, 0.17f);
        world.rotate(new Vector3(1f, 0f, 0f), 45f);

        this.instance.transform = world;
    }

    @Override
    public void render() {
        super.render();

        this.modelBatch.begin(this.camera);
        this.modelBatch.render(this.instance, this.environment);
        this.modelBatch.end();
    }
}