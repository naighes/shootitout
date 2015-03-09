package com.nukedbit.shootitout.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.physics.WorldObject;

public class Background extends com.nukedbit.framework.components.Sprite implements WorldObject {
    private float speed = 10f;

    private Vector2 position;

    protected Background(GameBase game, String texturePath) {
        super(game, texturePath);
    }

    @Override
    public void initialize() {
        super.initialize();

        final int viewPortWidth = this.getGame().getViewPort().getWidth();
        final int viewportHeight = this.getGame().getViewPort().getHeight();

        this.position = new Vector2(viewPortWidth /2 * -1f,
                                    viewportHeight /2 * -1f);

        final Texture texture = this.innerSprite.getTexture();
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        this.innerSprite.setPosition(this.position.x, this.position.y);
        this.innerSprite.setSize(Math.abs(texture.getWidth()), Math.abs(texture.getHeight()));
        this.innerSprite.setOrigin(texture.getWidth() / 2, texture.getHeight() / 2);
        this.innerSprite.setRegion(0,0, viewPortWidth, viewportHeight);

        this.innerSprite.setSize(texture.getWidth(), texture.getHeight() );
    }

    float scrollTimer= 0;
    @Override
    public void update(float dt) {
        super.update(dt);

        scrollTimer -= dt * 0.002f;
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