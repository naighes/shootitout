package com.nukedbit.shootitout;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Star extends SceneComponentBase {
    private final float x;
    private float y;
    private final int speed;

    public static Star create(int speed, Randomize random, Graphics graphics) {
        final int maxWidth = graphics.getWidth();
        final int maxHeight = graphics.getHeight() + 50;
        final int minWidth = 1;
        final int minHeight = 1;

        return new Star(random.Next(minWidth, maxWidth),
                        random.Next(minHeight, maxHeight),
                        speed);
    }

    private Star(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void initialize(Graphics graphics) {
        super.initialize(graphics);
    }

    @Override
    public void update(float delta, Graphics graphics) {
        super.update(delta, graphics);

        if (isOutOfScreenReset())
            reset(graphics);
        else
            y = y - (speed * delta);
    }

    @Override
    public void render(ShapeRenderer renderer) {
        super.render(renderer);

        renderer.point((int) x, (int) y, 0);
    }

    private boolean isOutOfScreenReset() {
        return y < 0;
    }

    private void reset(Graphics graphics) {
        y = graphics.getHeight() + 50;
    }
}