package com.nukedbit.shootitout;/* 
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
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends  SceneComponentBase {

    private Texture playerTexture;
    private SpriteBatch batch;
    private float y;
    private float x;
    private float height;
    private float width;

    public Player()
    {

    }

    @Override
    public void render(ShapeRenderer renderer) {
        batch.begin();
        batch.draw(playerTexture, this.getY(), this.getX(), this.getWidth(), this.getHeight());
        batch.end();
        super.render(renderer);
    }

    @Override
    public void update(float delta, Graphics graphics) {
        super.update(delta, graphics);
    }

    @Override
    public void initialize(Graphics graphics) {
        playerTexture = new Texture(Gdx.files.internal("player.png"));
        batch = new SpriteBatch();
        width = 150;
        height = 150;
        x = 100;
        y = 100;
        super.initialize(graphics);
    }

    private float getY() {
        return y;
    }

    private float getX() {
        return x;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
}
