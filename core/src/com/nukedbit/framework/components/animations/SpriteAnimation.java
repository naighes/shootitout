package com.nukedbit.framework.components.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponentBase;

public class SpriteAnimation extends GameComponentBase {
    private final float frameDuration;
    private final Rectangle[] frames;
    private final Sprite sprite;

    private Animation animation;
    private float stateTime = 0f;

    public SpriteAnimation(GameBase game,
                           float frameDuration,
                           Rectangle[] frames,
                           Sprite sprite) {
        super(game);

        this.frameDuration = frameDuration;
        this.frames = frames;
        this.sprite = sprite;
    }

    @Override
    public void initialize() {
        super.initialize();

        this.animation = new Animation(this.frameDuration, this.getFrames(this.frames));
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        this.sprite.setRegion(this.getCurrentFrame(dt));
    }

    private TextureRegion[] getFrames(Rectangle[] rectangles) {
        TextureRegion[] frames = new TextureRegion[rectangles.length];

        for (int i = 0; i < rectangles.length; i++) {
            frames[i] = new TextureRegion(this.sprite.getTexture(),
                                          (int) rectangles[i].x,
                                          (int) rectangles[i].y,
                                          (int) rectangles[i].width,
                                          (int) rectangles[i].height);
        }

        return frames;
    }

    private TextureRegion getCurrentFrame(float dt) {
        this.stateTime += dt;
        return this.animation.getKeyFrame(this.stateTime, true);
    }

    public Vector2 getFrameSize(float dt) {
        TextureRegion frame = this.getCurrentFrame(dt);
        return new Vector2(frame.getRegionWidth(), frame.getRegionHeight());
    }
}