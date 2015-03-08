package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.components.DrawableComponentBase;
import com.nukedbit.core.components.GameBase;

public class Bullet extends DrawableComponentBase {
    private final String texturePath;

    private Texture texture;
    private Vector2 position;
    private final float width;
    private final float height;

    public Bullet(GameBase game,
                  Vector2 initialPosition,
                  float width,
                  float height) {
        super(game);

        this.position = initialPosition;
        this.width = width;
        this.height = height;
        this.texturePath = "laser.png";
    }

    @Override
    public void initialize() {
        super.initialize();

        this.texture = new Texture(Gdx.files.internal(this.texturePath));
    }

    @Override
    public void update(float dt) {
        super.update(dt);
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
}