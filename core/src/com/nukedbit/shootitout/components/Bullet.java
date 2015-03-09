package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;

public class Bullet extends DrawableComponentBase {
    private final String texturePath;

    private Texture texture;
    private Vector2 position;

    public Bullet(GameBase game, Vector2 initialPosition) {
        super(game);

        this.position = initialPosition;
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
                      this.texture.getWidth(),
                      this.texture.getHeight());
        this.getGame().getSpriteBatch().end();

        super.render();
    }
}