package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.DrawableComponentBase;
import com.nukedbit.core.components.input.KeyboardInput;
import com.nukedbit.core.graphics.GraphicsAdapter;
import com.nukedbit.core.observing.Observer;

public class Player extends DrawableComponentBase implements Observer<KeyboardInput.KeyPressed> {
    private final float mass = 0.2f;
    private final float scalar = 58.0f;
    private final float inertia = -0.4f;

    private final float height;
    private final float width;
    private final String texturePath;

    private Texture playerTexture;
    private Vector2 position;
    private Vector2 acceleration = new Vector2(0f, 0f);
    private BitmapFont font;

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
        graphicsAdapter.getSpriteBatch().draw(playerTexture,
                this.position.x,
                this.position.y,
                this.width,
                this.height);
        this.font.draw(graphicsAdapter.getSpriteBatch(),
                       "velocity: (" + this.velocity.x + ", " + this.velocity.y + ")",
                       1f,
                       1f);
        this.font.draw(graphicsAdapter.getSpriteBatch(),
                       "acceleration: (" + this.acceleration.x + ", " + this.acceleration.y + ")",
                       1f,
                       this.font.getLineHeight());
        graphicsAdapter.getSpriteBatch().end();

        super.render(graphicsAdapter);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        this.velocity.add(calculateVelocity(this.acceleration, delta));
        Vector2 inverse = new Vector2(this.velocity.x * inertia,
                                      this.velocity.y * inertia);
        this.velocity.add(inverse);
        this.move();
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {
        this.playerTexture = new Texture(Gdx.files.internal(this.texturePath));
        this.font = new BitmapFont();
        font.setColor(0.5f, 0.4f, 0f, 1f);

        super.initialize(graphicsAdapter);
    }

    private void move() {
        this.position.add(this.velocity);
    }

    private final Vector2 velocity = new Vector2(0f, 0f);
    private final Vector2 left = new Vector2(-1f, 0f);
    private final Vector2 right = new Vector2(1f, 0f);
    private final Vector2 up = new Vector2(0f, -1f);
    private final Vector2 down = new Vector2(0f, 1f);
    private final Vector2 one = new Vector2(1f, 1f);
    private final Vector2 zero = new Vector2(0f, 0f);

    @Override
    public void notify(KeyboardInput.KeyPressed input) {
        Vector2 force = new Vector2();
        // force = m * a
        // a = force / m
        // v = v0 + 1/2at

        if (input.getKey() == Input.Keys.LEFT) {
            force.mulAdd(this.left, this.one);
        } else if (input.getKey() == Input.Keys.RIGHT) {
            force.mulAdd(this.right, this.one);
        }

        if (input.getKey() == Input.Keys.DOWN) {
            force.mulAdd(this.down, this.one);
        } else if (input.getKey() == Input.Keys.UP) {
            force.mulAdd(this.up, this.one);
        }

        this.acceleration = new Vector2((force.x * this.scalar) / this.mass,
                                        (force.y * this.scalar) / this.mass);
    }

    private Vector2 calculateVelocity(Vector2 acceleration, float dt) {
        return new Vector2(calculateVelocity(acceleration.x, dt),
                           calculateVelocity(acceleration.y, dt));
    }

    private float calculateVelocity(float acceleration, float dt) {
        return (0.5f) * (acceleration * (float) Math.pow(dt, 2f));
    }
}