package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.DrawableComponentBase;
import com.nukedbit.core.components.input.KeyboardInput;
import com.nukedbit.core.graphics.GraphicsAdapter;
import com.nukedbit.core.observing.Observer;

public class Player extends DrawableComponentBase implements Observer<KeyboardInput.KeyEvent> {
    private final float mass = 0.1f;
    private final float maxThrust = 168.0f;
    private final float airResistance = -0.1f;

    private final float height;
    private final float width;
    private final String texturePath;

    private Texture texture;
    private Vector2 position;
    private Vector2 direction = new Vector2(0f, 0f);
    private float thrust = 0f;

    public Player(Game game,
                  String texturePath,
                  float width,
                  float height,
                  Vector2 position)
    {
        super(game);

        this.texturePath = texturePath;
        this.width = width;
        this.height = height;
        this.position = position;
    }

    @Override
    public void render(GraphicsAdapter graphicsAdapter) {
        graphicsAdapter.getSpriteBatch().begin();
        graphicsAdapter.getSpriteBatch()
                       .draw(texture,
                             this.position.x,
                             this.position.y,
                             this.width,
                             this.height);
        graphicsAdapter.getSpriteBatch().end();

        super.render(graphicsAdapter);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (this.direction.x == 0f && this.direction.y == 0f) {
            this.thrust = 0f;
        } else {
            this.thrust = maxThrust;
        }

        Vector2 acceleration = new Vector2(this.direction.x * this.thrust / this.mass,
                                           this.direction.y * this.thrust / this.mass);
        this.velocity = calculateVelocity(acceleration, this.velocity, delta);

        Vector2 inverse = new Vector2(this.velocity.x * airResistance, this.velocity.y * airResistance);
        this.velocity.add(inverse);
        this.position.add(this.velocity);
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {
        this.texture = new Texture(Gdx.files.internal(this.texturePath));

        super.initialize(graphicsAdapter);
    }

    private Vector2 velocity = new Vector2(0f, 0f);

    private Vector2 calculateVelocity(Vector2 acceleration, Vector2 velocity, float dt) {
        return new Vector2(velocity.x + ((0.5f) * (acceleration.x * (float) Math.pow(dt, 2f))),
                           velocity.y + ((0.5f) * (acceleration.y * (float) Math.pow(dt, 2f))));
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
            this.direction.x = 0f;
        } else if (input.getKey() == Input.Keys.RIGHT) {
            this.direction.x = 0f;
        } else if (input.getKey() == Input.Keys.DOWN) {
            this.direction.y = 0f;
        } else if (input.getKey() == Input.Keys.UP) {
            this.direction.y = 0f;
        }
    }

    public void notify(KeyboardInput.KeyPressed input) {
        if (input.getKey() == Input.Keys.LEFT) {
            this.direction.x = -1f;
        } else if (input.getKey() == Input.Keys.RIGHT) {
            this.direction.x = 1f;
        } else if (input.getKey() == Input.Keys.DOWN) {
            this.direction.y = -1f;
        } else if (input.getKey() == Input.Keys.UP) {
            this.direction.y = 1f;
        }
    }
}