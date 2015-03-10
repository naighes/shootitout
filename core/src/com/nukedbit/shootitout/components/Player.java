package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponent;
import com.nukedbit.framework.components.SpriteComponent;
import com.nukedbit.framework.components.animations.SpriteAnimation;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.observing.Observer;
import com.nukedbit.framework.physics.RigidBody;
import com.nukedbit.framework.physics.WorldObject;

public class Player extends SpriteComponent implements Observer<KeyboardInput.KeyEvent>, WorldObject {
    private final float maxThrust;
    private final float scale;
    private final RigidBody body;
    private final Vector2 zero = new Vector2(0f, 0f);

    private Vector2 position;

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
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (this.body.getDirection() == this.zero) {
            this.body.setScalarForce(0f);
        } else {
            this.body.setScalarForce(this.maxThrust);
        }

        this.body.update(dt);

        SpriteAnimation animation = this.getCurrentAnimation();

        if (animation != null) {
            TextureRegion frame = animation.getCurrentFrame(dt);
            this.innerSprite.setRegion(frame);
            this.innerSprite.setSize(frame.getRegionWidth() * this.scale,
                                     frame.getRegionHeight() * this.scale);
        } else {
            this.innerSprite.setSize(this.texture.getWidth() * this.scale,
                                     this.texture.getHeight() * this.scale);
        }

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

    public void setCurrentAnimation(Rectangle[] frames, float frameDuration) {
        SpriteAnimation e = new SpriteAnimation(this.getGame(),
                                                frameDuration,
                                                frames,
                                                this.innerSprite);
        this.getComponents().add(e);
    }

    private SpriteAnimation getCurrentAnimation() {
        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);

            if (component instanceof SpriteAnimation) {
                return (SpriteAnimation) component;
            }
        }

        return null;
    }
}