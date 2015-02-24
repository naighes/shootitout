package com.nukedbit.shootitout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/*
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
public class StarLayer {

    private final ArrayList<Star> stars = new ArrayList<>();
    private final ShapesRenderer renderer;
    private final RandomValue random;

    class Star {
        private final float x;
        private float y;
        private final int speed;

        public Star(float x, float y, int speed) {

            this.x = x;
            this.y = y;
            this.speed = speed;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public void move(float delta) {
            y = y - (speed * delta);
        }

        public void reset() {
            y = Gdx.graphics.getHeight() + 50;
        }
    }

    public StarLayer(ShapesRenderer renderer, RandomValue random) {
        this.renderer = renderer;
        this.random = random;
    }

    public void initialize(int starsCount, int starSpeed) {
        for (int i = 0; i < starsCount; i++) {
            Star star = createStartAtRandomPosition(starSpeed);
            stars.add(star);
        }
    }

    private Star createStartAtRandomPosition(int starSpeed) {
        final int maxWidth = Gdx.graphics.getWidth();
        final int maxHeight = Gdx.graphics.getHeight() + 50;
        final int minWidth = 1;
        final int minHeight = 1;

        int x = random.Next(minWidth, maxWidth);
        int y = random.Next(minHeight, maxHeight);
        return new Star(x, y, starSpeed);
    }

    public void render(float delta) {
        renderer.begin(ShapeRenderer.ShapeType.Point);
        renderer.setColor(Color.WHITE);
        for (Star star : stars) {
            renderStar(star);
            star.move(delta);
            starIsOutOfScreenReset(star);
        }
        renderer.end();
    }

    private void starIsOutOfScreenReset(Star star) {
        if (star.getY() < 0) {
            star.reset();
        }
    }

    private void renderStar(Star star) {
        renderer.point((int) star.getX(), (int) star.getY(), 0);
    }
}
