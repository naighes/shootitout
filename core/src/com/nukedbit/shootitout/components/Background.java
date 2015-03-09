package com.nukedbit.shootitout.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.physics.WorldObject;

public class Background extends com.nukedbit.framework.components.Sprite implements WorldObject {
    private final float speedFactor = 0.002f;

    protected Background(GameBase game, String texturePath) {
        super(game, texturePath);
    }

    @Override
    public void initialize() {
        super.initialize();

        final int viewPortWidth = this.getGame().getViewPort().getWidth();
        final int viewportHeight = this.getGame().getViewPort().getHeight();

        setTextureWrap();

        setSpriteBounds(viewPortWidth, viewportHeight);
    }

    private void setTextureWrap() {
        final Texture texture = this.innerSprite.getTexture();
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    private void setSpriteBounds(int viewPortWidth, int viewportHeight) {
        final Texture texture = this.innerSprite.getTexture();
        Vector2 position = new Vector2(viewPortWidth /2 * -1f,
                viewportHeight /2 * -1f);
        this.innerSprite.setSize(texture.getWidth(), texture.getHeight());
        this.innerSprite.setPosition(position.x, position.y);
        this.innerSprite.setSize(Math.abs(texture.getWidth()), Math.abs(texture.getHeight()));
        this.innerSprite.setOrigin(texture.getWidth() / 2, texture.getHeight() / 2);
        this.innerSprite.setRegion(0,0, viewPortWidth, viewportHeight);
    }

    float scrollTimer= 0;
    @Override
    public void update(float dt) {
        super.update(dt);

        scrollTimer -= dt * speedFactor;
        if(scrollTimer>1.0f)
            scrollTimer = 0.0f;

        innerSprite.setV(scrollTimer);
        innerSprite.setV2(scrollTimer + 1);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }
}