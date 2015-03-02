package com.nukedbit.shootitout.components;

import com.nukedbit.shootitout.Game;
import com.nukedbit.shootitout.graphics.GraphicsAdapter;
import com.nukedbit.shootitout.utils.Randomize;

public class Star extends DrawableComponentBase {
    private final float x;
    private float y;
    private final int speed;

    public static Star create(Game game,
                              int speed,
                              Randomize random) {
        final int maxWidth = game.getViewPort().getWidth();
        final int maxHeight = game.getViewPort().getHeight() + 50;
        final int minWidth = 1;
        final int minHeight = 1;

        return new Star(game,
                        random.Next(minWidth, maxWidth),
                        random.Next(minHeight, maxHeight),
                        speed);
    }

    private Star(Game game, float x, float y, int speed) {
        super(game);

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
            reset();
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

    private void reset() {
        y = this.getGame().getViewPort().getHeight() + 50;
    }
}