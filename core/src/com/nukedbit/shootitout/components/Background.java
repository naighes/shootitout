package com.nukedbit.shootitout.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.graphics.ViewPort;

public class Background extends com.nukedbit.framework.components.Sprite {
    private final float speedFactor = 0.002f;
    private float scrollTimer = 0f;

    protected Background(GameBase game, String texturePath) {
        super(game, texturePath);
    }

    @Override
    public void initialize() {
        super.initialize();

        this.setTextureWrap();
        this.setSpriteBounds(this.getGame().getViewPort());
    }

    private void setTextureWrap() {
        final Texture texture = this.innerSprite.getTexture();
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    private void setSpriteBounds(ViewPort viewPort) {
        final Texture texture = this.innerSprite.getTexture();
        Vector2 position = new Vector2(0f, 0f);
        this.innerSprite.setPosition(position.x, position.y);
        this.innerSprite.setSize(texture.getWidth(), texture.getHeight());
        this.innerSprite.setOrigin(texture.getWidth() / 2f, texture.getHeight() / 2f);
        this.innerSprite.setRegion(0f, 0f, viewPort.getWidth(), viewPort.getHeight());
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.scrollTimer -= dt * this.speedFactor;

        if (this.scrollTimer > 1.0f)
            this.scrollTimer = 0.0f;

        this.innerSprite.setU(1f);
        this.innerSprite.setU2(0f);
        this.innerSprite.setV(this.scrollTimer);
        this.innerSprite.setV2(this.scrollTimer + 1f);
    }

    @Override
    public void render() {
        super.render();
    }
}