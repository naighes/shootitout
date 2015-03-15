package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponent;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.observing.Observer;
import com.nukedbit.framework.physics.RigidBody;

public class Enemy extends DrawableComponentBase implements Observer<KeyboardInput.KeyEvent> {
    private Vector3 position;
    private Vector3 direction;
    private final Matrix4 initialRotation;
    private Matrix4 transform;

    private final float scale;
    public Environment environment;
    public ModelBatch modelBatch;
    public ModelInstance instance;

    private final RigidBody body;
    private final Vector2 zero = new Vector2(0f, 0f);
    private float maxThrust = 5.0f;

    private FileHandle shootSoundFileHandle;
    private Sound sound;

    protected Enemy(GameBase game) {
        super(game);

        // positive z is outside screen.
        this.position = new Vector3(0f, 0f, -4f);
        this.direction = new Vector3(0f, 1f, 0f);
        this.initialRotation = new Matrix4().rotate(new Vector3(0f, 1f, 0f), 180f)
                                            .rotate(new Vector3(1f, 0f, 0f), -90f);
        this.scale = 0.5f;

        this.body = new RigidBody(new Vector2(0f, 0f),
                                  new Vector2(0f, 0f),
                                  0.1f,
                                  0f,
                                  new com.nukedbit.framework.physics.Environment(0.1f));
    }

    @Override
    public void initialize() {
        super.initialize();

        this.shootSoundFileHandle = Gdx.files.getFileHandle("science_fiction_laser_007.mp3",
                                                            Files.FileType.Absolute);
        this.sound = Gdx.audio.newSound(shootSoundFileHandle);

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

        this.updateBody(dt);

        Vector2 velocity = this.body.getVelocity();
        this.position.x += velocity.x;
        this.position.y += velocity.y;

        Matrix4 localTransform = new Matrix4();

        localTransform.mul(this.transform);
        localTransform.mul(new Matrix4().setToTranslation(this.position));
        localTransform.mul(new Matrix4().scale(scale, scale, scale));
        localTransform.mul(initialRotation);

        this.instance.transform = localTransform;
    }

    private void updateBody(float dt) {
        if (this.body.getDirection() == zero) {
            this.body.setScalarForce(0f);
        } else {
            this.body.setScalarForce(this.maxThrust);
        }

        this.body.update(dt);
    }

    @Override
    public void render() {
        super.render();

        this.modelBatch.begin(this.getGame().getActiveCamera().getInnerCamera());
        this.modelBatch.render(this.instance, this.environment);
        this.modelBatch.end();
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
            this.body.setDirection(0f, this.body.getDirection().y);
        } else if (input.getKey() == Input.Keys.RIGHT) {
            this.body.setDirection(0f, this.body.getDirection().y);
        } else if (input.getKey() == Input.Keys.DOWN) {
            this.body.setDirection(this.body.getDirection().x, 0f);
        } else if (input.getKey() == Input.Keys.UP) {
            this.body.setDirection(this.body.getDirection().x, 0f);
        } else if (input.getKey() == Input.Keys.SPACE) {
            this.shoot();
        }
    }

    private void shoot() {
        GameComponent bullet = new Bullet(this.getGame(), new Vector3(this.position.x,
                                                                      this.position.y,
                                                                      this.position.z));
        this.getGame().getComponents().add(bullet);
        sound.play();
    }

    public void notify(KeyboardInput.KeyPressed input) {
        if (input.getKey() == Input.Keys.LEFT) {
            this.body.setDirection(-1f, this.body.getDirection().y);
        } else if (input.getKey() == Input.Keys.RIGHT) {
            this.body.setDirection(1f, this.body.getDirection().y);
        } else if (input.getKey() == Input.Keys.DOWN) {
            this.body.setDirection(this.body.getDirection().x, -1f);
        } else if (input.getKey() == Input.Keys.UP) {
            this.body.setDirection(this.body.getDirection().x, 1f);
        }
    }
}