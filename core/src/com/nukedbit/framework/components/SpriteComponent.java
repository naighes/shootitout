package com.nukedbit.framework.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpriteComponent extends DrawableComponentBase {
    protected com.badlogic.gdx.graphics.g2d.Sprite innerSprite;
    private final String texturePath;

    protected Texture texture;

    public SpriteComponent(GameBase game, String texturePath) {
        super(game);

        this.texturePath = texturePath;
    }

    @Override
    public void initialize() {
        this.texture = new Texture(Gdx.files.internal(this.texturePath));
        this.innerSprite = new com.badlogic.gdx.graphics.g2d.Sprite(this.texture);

        super.initialize();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void render() {
        this.getGame().getSpriteBatch().begin();
        this.innerSprite.draw(this.getGame().getSpriteBatch());
        this.getGame().getSpriteBatch().end();

        super.render();
    }
}