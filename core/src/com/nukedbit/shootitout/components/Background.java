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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.nukedbit.core.components.DrawableComponentBase;
import com.nukedbit.core.components.GameBase;

public class Background extends DrawableComponentBase {

    private final String backgroundTexturePath;
    private Texture texture;
    private Sprite sprite;
    private float speed = 10;

    protected Background(GameBase game, String backgroundTexturePath) {
        super(game);
        this.backgroundTexturePath = backgroundTexturePath;
    }

    @Override
    public void initialize() {
        super.initialize();
        texture = new Texture(Gdx.files.internal(this.backgroundTexturePath));
        sprite = new Sprite(texture);
        sprite.setPosition(this.getGame().getViewPort().getWidth()/2 * -1,
                this.getGame().getViewPort().getHeight()/2 * -1);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        sprite.setPosition(sprite.getX(),sprite.getY() - speed * dt);
    }

    @Override
    public void render() {
        super.render();
        this.getGame().getSpriteBatch().begin();
        this.sprite.draw(this.getGame().getSpriteBatch());
        this.getGame().getSpriteBatch().end();
    }
}
