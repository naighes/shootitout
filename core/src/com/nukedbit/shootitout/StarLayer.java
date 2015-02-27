package com.nukedbit.shootitout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class StarLayer implements SceneComponent {
    private final ArrayList<Star> stars = new ArrayList<>();
    private final int starsCount;
    private final int starSpeed;
    private final Randomize random;

    public StarLayer(int starsCount, int starSpeed, Randomize random) {
        this.starsCount = starsCount;
        this.starSpeed = starSpeed;
        this.random = random;
    }

    public void initialize() {
        for (int i = 0; i < starsCount; i++) {
            Star star = createStartAtRandomPosition(starSpeed);
            stars.add(star);
        }
    }

    @Override
    public void update(float delta) {
        for (Star star : stars){
            star.update(delta);
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

    public void render(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Point);
        renderer.setColor(Color.WHITE);

        for (Star star : stars) {
            star.render(renderer);
        }

        renderer.end();
    }

    public class Star implements SceneComponent {
        private final float x;
        private float y;
        private final int speed;

        public Star(float x, float y, int speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }

        private void reset() {
            y = Gdx.graphics.getHeight() + 50;
        }

        @Override
        public void initialize() {
        }

        @Override
        public void update(float delta) {
            if (isOutOfScreenReset())
                reset();
            else
                y = y - (speed * delta);
        }

        @Override
        public void render(ShapeRenderer renderer) {
            renderer.point((int) x, (int) y, 0);
        }

        private boolean isOutOfScreenReset() {
            return y < 0;
        }
    }
}