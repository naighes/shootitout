package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimation {
    private final String texturePath;
    private final int FRAME_COLS = 1;
    private final int FRAME_ROWS = 15;

    private Animation animation;
    private Texture texture;
    private float stateTime = 0f;

    public PlayerAnimation(String texturePath) {
        this.texturePath = texturePath;
    }

    public void initialize() {
        this.texture = new Texture(Gdx.files.internal(this.texturePath));
        TextureRegion[][] tmp = TextureRegion.split(this.texture,
                                                    this.texture.getWidth() / FRAME_COLS,
                                                    this.texture.getHeight() / FRAME_ROWS);
        this.animation = new Animation(0.040f, getAnimationFrames(tmp));
    }

    private TextureRegion[] getAnimationFrames(TextureRegion[][] tmp) {
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;

        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return frames;
    }

    public TextureRegion getCurrentFrame(float deltaTime) {
        this.stateTime += deltaTime;
        return this.animation.getKeyFrame(this.stateTime, true);
    }
}