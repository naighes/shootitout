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
import com.nukedbit.core.physics.RigidBody;

public class Player extends DrawableComponentBase implements Observer<KeyboardInput.KeyEvent> {
    private final float maxThrust = 168.0f; // TODO: initialize by ctor.

    private final float height;
    private final float width;
    private final String texturePath;

    private Texture texture;
    private Vector2 position;
    private final RigidBody body;

    public Player(Game game,
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
        }
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