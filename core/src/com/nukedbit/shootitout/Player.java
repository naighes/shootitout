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
import com.badlogic.gdx.Input;
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
    private float speed;

    public Player()
    {

    }

    @Override
    public void render(ShapeRenderer renderer) {
        batch.begin();
        batch.draw(playerTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        batch.end();
        super.render(renderer);
    }

    @Override
    public void update(float delta, Graphics graphics) {
        float deltaMov = speed * delta;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.setX((this.speed * deltaMov) * -1);
        }else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.setX(this.speed * deltaMov);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            this.setY(this.speed * deltaMov);
        }else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            this.setY((this.speed * deltaMov) * -1);
        }

        super.update(delta, graphics);
    }

    @Override
    public void initialize(Graphics graphics) {
        playerTexture = new Texture(Gdx.files.internal("player.png"));
        batch = new SpriteBatch();
        width = 80;
        height = 80;
        x = 100;
        y = 100;
        speed = 20;
        super.initialize(graphics);
    }

    private void setY(float y)
    {
        this.y += y;
    }

    private void setX(float x){
        this.x += x;
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
