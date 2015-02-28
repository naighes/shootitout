package com.nukedbit.shootitout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Player extends SceneComponentBase {
    private Texture playerTexture;
    private final float height;
    private final float width;
    private float y;
    private float x;
    private float speed;
    private final String texturePath;

    public Player(String texturePath,
                  float width,
                  float height,
                  float x,
                  float y,
                  float speed)
    {
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
        float deltaMov = speed * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.move((this.speed * deltaMov) * -1, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.move(this.speed * deltaMov, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.move(0, this.speed * deltaMov);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.move(0, (this.speed * deltaMov) * -1);
        }

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
}