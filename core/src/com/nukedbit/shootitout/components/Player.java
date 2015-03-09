package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.AnimatedSprite;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponent;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.observing.Observer;
import com.nukedbit.framework.physics.RigidBody;
import com.nukedbit.framework.physics.WorldObject;

public class Player extends AnimatedSprite implements Observer<KeyboardInput.KeyEvent>, WorldObject {
    private final float maxThrust;
    private final float scale;
    private final RigidBody body;
    private final Vector2 zero = new Vector2(0f, 0f);

    private Vector2 position;
    private final float spriteOriginalWidth = 268f;
    private final float spriteOriginalHeight = 324.33f;

    public Player(GameBase game,
                  String texturePath,
                  Vector2 initialPosition,
                  RigidBody body,
                  float scale,
                  float maxThrust) {
        super(game, texturePath, 1, 15);

        this.position = initialPosition;
        this.body = body;
        this.maxThrust = maxThrust;
        this.scale = scale;
    }

    @Override
    public void initialize() {
        super.initialize();

        this.innerSprite.setSize(spriteOriginalWidth * scale,
                spriteOriginalHeight * scale);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (this.body.getDirection() == zero) {
            this.body.setScalarForce(0f);
        } else {
            this.body.setScalarForce(maxThrust);
        }

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
                new Vector2(this.getPosition().x + (this.innerSprite.getWidth() / 2), (this.getPosition().y + this.innerSprite.getHeight())));
        bullet.initialize(); // TODO: not good doing that here.
        GameComponent bullet2 = new Bullet(this.getGame(),
                new Vector2(this.getPosition().x + (this.innerSprite.getWidth() / 2) - 10, (this.getPosition().y + this.innerSprite.getHeight())));
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
}