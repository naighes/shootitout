package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Graphics;
import com.nukedbit.shootitout.graphics.GraphicsAdapter;
import com.nukedbit.shootitout.utils.Randomize;

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
    public void initialize(GraphicsAdapter graphicsAdapter) {
        super.initialize(graphicsAdapter);
    }

    @Override
    public void update(float delta, GraphicsAdapter graphicsAdapter) {
        super.update(delta, graphicsAdapter);

        if (isOutOfScreenReset())
            reset(graphicsAdapter.getGraphics());
        else
            y = y - (speed * delta);
    }

    @Override
    public void render(GraphicsAdapter graphicsAdapter) {
        super.render(graphicsAdapter);

        graphicsAdapter.getRenderer().point((int) x, (int) y, 0);
    }

    private boolean isOutOfScreenReset() {
        return y < 0;
    }

    private void reset(Graphics graphics) {
        y = graphics.getHeight() + 50;
    }
}