package com.nukedbit.framework.components;/* 
* Copyright 2015 Sebastian Faltoni
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*     http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedSprite extends Sprite {
    private final int frameColumns;
    private final int frameRows;
    private float frameDuration;
    private Animation animation;

    public AnimatedSprite(GameBase game, String texturePath, int frameColumns, int frameRows, float frameDuration) {
        super(game, texturePath);
        this.frameColumns = frameColumns;
        this.frameRows = frameRows;
        this.frameDuration = frameDuration;
    }

    @Override
    public void initialize() {
        this.texture = new Texture(Gdx.files.internal(this.texturePath));
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / frameColumns, texture.getHeight() / frameRows);
        animation = new Animation(frameDuration, getAnimationFrames(tmp));
        this.innerSprite = new com.badlogic.gdx.graphics.g2d.Sprite(getCurrentFrame(0));
    }

    private TextureRegion[] getAnimationFrames(TextureRegion[][] tmp) {
        TextureRegion[] animationFrames = new TextureRegion[frameColumns * frameRows];

        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColumns; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
        return animationFrames;
    }

    float stateTime = 0;
    protected TextureRegion getCurrentFrame(float deltaTime) {
        stateTime += deltaTime;
        return animation.getKeyFrame(stateTime, true);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        this.innerSprite.setRegion(this.getCurrentFrame(dt));
    }
}
