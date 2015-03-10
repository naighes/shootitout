package com.nukedbit.framework.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.animations.SpriteAnimation;

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

    protected Vector2 getSize(float dt) {
        SpriteAnimation animation = this.getCurrentAnimation();

        if (animation != null) {
            return animation.getFrameSize(dt);
        }

        return new Vector2(this.texture.getWidth(), this.texture.getHeight());
    }

    @Override
    public void render() {
        this.getGame().getSpriteBatch().begin();
        this.innerSprite.draw(this.getGame().getSpriteBatch());
        this.getGame().getSpriteBatch().end();

        super.render();
    }

    protected SpriteAnimation getCurrentAnimation() {
        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);

            if (component instanceof SpriteAnimation) {
                return (SpriteAnimation) component;
            }
        }

        return null;
    }

    public void setCurrentAnimation(Rectangle[] frames, float frameDuration) {
        SpriteAnimation animation = new SpriteAnimation(this.getGame(),
                                                        frameDuration,
                                                        frames,
                                                        this.innerSprite);
        this.getComponents().add(animation);
    }
}