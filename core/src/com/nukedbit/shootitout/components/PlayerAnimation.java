package com.nukedbit.shootitout.components;/* 
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

public class PlayerAnimation {
    private final String texturePath;
    private Animation defaultAnimation;
    private final int FRAME_COLS = 1;
    private final int FRAME_ROWS = 16;
    private Texture animationTexture;
    private float stateTime = 0f;

    public void initialize() {
        this.animationTexture = new Texture(Gdx.files.internal(this.texturePath));
        TextureRegion[][] tmp = TextureRegion.split(animationTexture, animationTexture.getWidth() / FRAME_COLS, animationTexture.getHeight() / FRAME_ROWS);
        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
        defaultAnimation = new Animation(0.040f, animationFrames);
    }

    public TextureRegion getCurrentFrame(float deltaTime) {
        stateTime += deltaTime;
        return defaultAnimation.getKeyFrame(stateTime, true);
    }

    public PlayerAnimation(String texturePath) {
        this.texturePath = texturePath;
    }
}
