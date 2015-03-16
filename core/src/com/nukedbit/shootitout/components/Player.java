package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.observing.Observer;
import com.nukedbit.framework.physics.RigidBody;
import com.nukedbit.framework.physics.WorldObject;
import com.nukedbit.shootitout.BulletBuilder;

public class Player extends DrawableComponentBase implements Observer<KeyboardInput.KeyEvent>, WorldObject {
    private final Matrix4 initialRotation;
    private Vector3 position;
    private final float scale;
    private Matrix4 transform;

    private final float maxThrust;
    private final RigidBody body;

    private ModelBatch batch;
    private ModelInstance instance;

    private final BulletBuilder bulletBuilder;

    protected Player(GameBase game,
                     Matrix4 initialRotation,
                     Vector3 position,
                     float scale,
                     float maxThrust,
                     RigidBody body,
                     BulletBuilder bulletBuilder) {
        super(game);

        this.initialRotation = initialRotation;
        this.position = position;
        this.scale = scale;
        this.maxThrust = maxThrust;
        this.body = body;
        this.bulletBuilder = bulletBuilder;
    }

    @Override
    public void initialize() {
        super.initialize();

        this.batch = new ModelBatch();
        ModelLoader<?> loader = new ObjLoader();
        this.instance = new ModelInstance(loader.loadModel(Gdx.files.internal("ship/ship.obj")));
        this.transform = this.instance.transform.cpy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.updateBody(dt);
        this.position.add(this.body.getVelocity());

        Matrix4 localTransform = new Matrix4();

        localTransform.mul(this.transform);
        localTransform.mul(new Matrix4().setToTranslation(this.position));
        localTransform.mul(new Matrix4().scale(scale, scale, scale));
        localTransform.mul(initialRotation);

        this.instance.transform = localTransform;
    }

    private void updateBody(float dt) {
        if (this.body.getDirection() == Vector3.Zero) {
            this.body.setScalarForce(0f);
        } else {
            this.body.setScalarForce(this.maxThrust);
        }

        this.body.update(dt);
    }

    @Override
    public void render() {
        super.render();

        this.batch.begin(this.getGame().getActiveCamera().getInnerCamera());
        this.batch.render(this.instance, this.getGame().getEnvironment().getInnerEnvironment());
        this.batch.end();
    }

    @Override
    public void notify(KeyboardInput.KeyEvent input) {
        if (input instanceof KeyboardInput.KeyPressed) {
            this.notify((KeyboardInput.KeyPressed) input);
        }

        if (input instanceof KeyboardInput.KeyReleased) {
            this.notify((KeyboardInput.KeyReleased) input);
        }
    }

    public void notify(KeyboardInput.KeyReleased input) {
        if (input.getKey() == Input.Keys.LEFT) {
            this.body.setDirection(0f, this.body.getDirection().y, 0f);
        } else if (input.getKey() == Input.Keys.RIGHT) {
            this.body.setDirection(0f, this.body.getDirection().y, 0f);
        } else if (input.getKey() == Input.Keys.DOWN) {
            this.body.setDirection(this.body.getDirection().x, 0f, 0f);
        } else if (input.getKey() == Input.Keys.UP) {
            this.body.setDirection(this.body.getDirection().x, 0f, 0f);
        } else if (input.getKey() == Input.Keys.SPACE) {
            this.bulletBuilder.shootAt(this.position);
        }
    }

    public void notify(KeyboardInput.KeyPressed input) {
        if (input.getKey() == Input.Keys.LEFT) {
            this.body.setDirection(-1f, this.body.getDirection().y, 0f);
        } else if (input.getKey() == Input.Keys.RIGHT) {
            this.body.setDirection(1f, this.body.getDirection().y, 0f);
        } else if (input.getKey() == Input.Keys.DOWN) {
            this.body.setDirection(this.body.getDirection().x, -1f, 0f);
        } else if (input.getKey() == Input.Keys.UP) {
            this.body.setDirection(this.body.getDirection().x, 1f, 0f);
        }
    }

    @Override
    public Vector3 getPosition() {
        return this.position;
    }
}