package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponent;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.components.Sprite;
import com.nukedbit.framework.observing.Observer;
import com.nukedbit.framework.physics.RigidBody;
import com.nukedbit.framework.physics.WorldObject;

public class Player extends Sprite implements Observer<KeyboardInput.KeyEvent>, WorldObject {
    private final float maxThrust;
    private final float scale;
    private final RigidBody body;
    private final Vector2 zero = new Vector2(0f, 0f);

    private final int FRAME_COLS = 1;
    private final int FRAME_ROWS = 15;

    private Vector2 position;
    private Animation animation;
    private float stateTime = 0f;

    public Player(GameBase game,
                  String texturePath,
                  Vector2 initialPosition,
                  RigidBody body,
                  float scale,
                  float maxThrust)
    {
        super(game, texturePath);

        this.position = initialPosition;
        this.body = body;
        this.maxThrust = maxThrust;
        this.scale = scale;
    }

    @Override
    public void initialize() {
        super.initialize();

        TextureRegion[][] regions = getRegions();
        this.animation = new Animation(0.040f, getFrames(regions));
        this.innerSprite.setSize(this.innerSprite.getWidth() * scale,
                                 this.innerSprite.getWidth() * scale);
    }

    private TextureRegion[][] getRegions() {
        return TextureRegion.split(this.texture,
                                   this.texture.getWidth() / FRAME_COLS,
                                   this.texture.getHeight() / FRAME_ROWS);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (this.body.getDirection() == zero) {
            this.body.setScalarForce(0f);
        } else {
            this.body.setScalarForce(maxThrust);
        }

        this.innerSprite.setRegion(this.getCurrentFrame(dt));
        this.body.update(dt);
        this.position.add(this.body.getVelocity());
        this.innerSprite.setPosition(this.position.x, this.position.y);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void notify(KeyboardInput.KeyEvent input) {
        if (input instanceof KeyboardInput.KeyPressed) {
            notify((KeyboardInput.KeyPressed) input);
        }

        if (input instanceof KeyboardInput.KeyReleased) {
            notify((KeyboardInput.KeyReleased) input);
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
        GameComponent bullet = new Bullet(this.getGame(),
                new Vector2(this.getPosition().x, this.getPosition().y));
        bullet.initialize(); // TODO: not good doing that here.
        this.getGame().getComponents().add(bullet);
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

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    private TextureRegion[] getFrames(TextureRegion[][] regions) {
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;

        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = regions[i][j];
            }
        }

        return frames;
    }

    public TextureRegion getCurrentFrame(float deltaTime) {
        this.stateTime += deltaTime;
        return this.animation.getKeyFrame(this.stateTime, true);
    }
}