package com.nukedbit.shootitout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class StarLayer {

    private final ArrayList<Star> stars = new ArrayList<Star>();
    private final ShapesRenderer renderer;

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

    public StarLayer(ShapesRenderer renderer) {
        this.renderer = renderer;
    }



    public void initialize(int starsCount, int starSpeed) {
        Random random = new Random();
        final int maxWidth = Gdx.graphics.getWidth();
        final int maxHeight = Gdx.graphics.getHeight() + 50;
        final int minWidth = 1;
        final int minHeight = 1;

        for (int i = 0; i < starsCount; i++) {
            int x = random.nextInt((maxWidth - minWidth) + 1) + minWidth;
            int y = random.nextInt((maxHeight - minHeight) + 1) + minHeight;
            Star star = new Star(x, y, starSpeed);
            stars.add(star);
        }
    }

    public void render(float delta) {
        renderer.begin(ShapeRenderer.ShapeType.Point);
        renderer.setColor(Color.WHITE);

        for (int startIndex = 0; startIndex < stars.size(); startIndex++) {
            Star star = stars.get(startIndex);
            renderer.point((int) star.getX(), (int) star.getY(), 0);
            star.move(delta);
            if (star.getY() < 0) {
                star.reset();
            }
        }
        renderer.end();
    }
}
