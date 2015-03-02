package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.nukedbit.shootitout.Game;
import com.nukedbit.shootitout.components.input.KeyboardInput;
import com.nukedbit.shootitout.graphics.GraphicsAdapter;
import com.nukedbit.shootitout.observing.Observer;

public class Player extends DrawableComponentBase implements Observer<KeyboardInput.KeyPressed> {
    private Texture playerTexture;
    private final float height;
    private final float width;
    private float y;
    private float x;
    private float speed;
    private final String texturePath;

    public Player(Game game,
                  String texturePath,
                  float width,
                  float height,
                  float x,
                  float y,
                  float speed)
    {
        super(game);

        this.texturePath = texturePath;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void render(GraphicsAdapter graphicsAdapter) {
        graphicsAdapter.getSpriteBatch().begin();
        graphicsAdapter.getSpriteBatch().draw(playerTexture, x, y, width, height);
        graphicsAdapter.getSpriteBatch().end();

        super.render(graphicsAdapter);
    }

    @Override
    public void update(float delta, GraphicsAdapter graphicsAdapter) {
        super.update(delta, graphicsAdapter);
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {
        playerTexture = new Texture(Gdx.files.internal(texturePath));

        super.initialize(graphicsAdapter);
    }

    private void move(float x, float y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void notify(KeyboardInput.KeyPressed input) {
        float newSpeed = this.speed * input.getDelta();

        if (input.getKey() == Input.Keys.LEFT) {
            this.move(newSpeed * -1, 0);
        } else if (input.getKey() == Input.Keys.RIGHT) {
            this.move(newSpeed, 0);
        }

        if (input.getKey() == Input.Keys.UP) {
            this.move(0, newSpeed);
        } else if (input.getKey() == Input.Keys.DOWN) {
            this.move(0, newSpeed * -1);
        }
    }
}