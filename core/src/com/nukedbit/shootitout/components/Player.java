package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.components.DrawableComponentBase;
import com.nukedbit.core.components.GameBase;
import com.nukedbit.core.components.GameComponent;
import com.nukedbit.core.components.input.KeyboardInput;
import com.nukedbit.core.observing.Observer;
import com.nukedbit.core.physics.RigidBody;
import com.nukedbit.core.physics.WorldObject;

public class Player extends DrawableComponentBase implements Observer<KeyboardInput.KeyEvent>, WorldObject {
    private final float height;
    private final float width;
    private final String texturePath;
    private final float maxThrust;

    private Texture texture;
    private Vector2 position;
    private final RigidBody body;

    public Player(GameBase game,
                  String texturePath,
                  float width,
                  float height,
                  Vector2 initialPosition,
                  RigidBody body)
    {
        super(game);

        this.texturePath = texturePath;
        this.width = width;
        this.height = height;
        this.position = initialPosition;
        this.body = body;
        this.maxThrust = 1040.0f;
    }

    @Override
    public void render() {
        this.getGame().getSpriteBatch().begin();
        this.getGame().getSpriteBatch()
                      .draw(texture,
                            this.position.x,
                            this.position.y,
                            this.width,
                            this.height);
        this.getGame().getSpriteBatch().end();

        super.render();
    }

    private final Vector2 zero = new Vector2(0f, 0f);

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
    }

    @Override
    public void initialize() {
        this.texture = new Texture(Gdx.files.internal(this.texturePath));

        super.initialize();
    }

    @Override
    public void notify(KeyboardInput.KeyEvent input) {
        if (input instanceof KeyboardInput.KeyPressed) {
            notify((KeyboardInput.KeyPressed) input);
        }

        if (input instanceof KeyboardInput.KeyReleased){
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
                                          new Vector2(this.getPosition().x, this.getPosition().y),
                                          64f,
                                          64f);
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
}