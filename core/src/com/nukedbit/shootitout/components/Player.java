package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponent;
import com.nukedbit.framework.components.Sprite;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.observing.Observer;
import com.nukedbit.framework.physics.RigidBody;
import com.nukedbit.framework.physics.WorldObject;

public class Player extends Sprite implements Observer<KeyboardInput.KeyEvent>, WorldObject {
    private final float maxThrust;
    private final float scale;
    private final Rectangle[] frames;
    private final RigidBody body;
    private final Vector2 zero = new Vector2(0f, 0f);

    private Vector2 position;
    private final float width = 268f; // TODO: should be "injected".
    private final float height = 324.33f; // TODO: should be "injected".

    private Animation animation;
    private final float frameDuration;
    private float stateTime = 0f;

    public Player(GameBase game,
                  String texturePath,
                  Vector2 initialPosition,
                  RigidBody body,
                  float scale,
                  float maxThrust,
                  Rectangle[] frames)
    {
        super(game, texturePath);

        this.position = initialPosition;
        this.body = body;
        this.maxThrust = maxThrust;
        this.scale = scale;
        this.frames = frames;
        this.frameDuration = 0.040f; // TODO: should be "injected".
    }

    @Override
    public void initialize() {
        super.initialize();

        this.animation = new Animation(frameDuration, this.getFrames(this.frames));
        this.innerSprite.setSize(this.width * this.scale, this.height * this.scale);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (this.body.getDirection() == this.zero) {
            this.body.setScalarForce(0f);
        } else {
            this.body.setScalarForce(this.maxThrust);
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
                                          new Vector2(this.getPosition().x + this.innerSprite.getWidth() / 2f,
                                                      this.getPosition().y + this.innerSprite.getHeight()));
        bullet.initialize(); // TODO: not good doing that here.
        GameComponent bullet2 = new Bullet(this.getGame(),
                                           new Vector2(this.getPosition().x + this.innerSprite.getWidth() / 2f - 10f,
                                                       this.getPosition().y + this.innerSprite.getHeight()));
        bullet2.initialize(); // TODO: not good doing that here.
        this.getGame().getComponents().add(bullet);
        this.getGame().getComponents().add(bullet2);
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

    private TextureRegion[] getFrames(Rectangle[] rectangles) {
        TextureRegion[] frames = new TextureRegion[rectangles.length];

        for (int i = 0; i < rectangles.length; i++) {
            frames[i] = new TextureRegion(texture,
                                          (int) rectangles[i].x,
                                          (int) rectangles[i].y,
                                          (int) rectangles[i].width,
                                          (int) rectangles[i].height);
        }

        return frames;
    }

    private TextureRegion getCurrentFrame(float dt) {
        this.stateTime += dt;
        return this.animation.getKeyFrame(this.stateTime, true);
    }
}