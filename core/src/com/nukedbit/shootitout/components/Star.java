package com.nukedbit.shootitout.components;

import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.utils.Randomize;

public class Star extends DrawableComponentBase {
    private final float x;
    private final float glowColor;
    private float y;
    private final float color;
    private final int speed;

    public static Star create(GameBase game,
                              int speed,
                              Randomize random) {
        final int maxWidth = game.getViewPort().getWidth();
        final int maxHeight = game.getViewPort().getHeight() + 50;
        final int minWidth = 1;
        final int minHeight = 1;

        return new Star(game,
                        random.next(minWidth, maxWidth),
                        random.next(minHeight, maxHeight),
                        1f,
                        speed);
    }

    private Star(GameBase game, float x, float y, float color, int speed) {
        super(game);

        this.x = x;
        this.y = y;
        this.color = color;
        this.glowColor = color / 2f;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (isOutOfScreenReset())
            reset();
        else
            y = y - (speed * dt);
    }

    @Override
    public void render() {
        super.render();

        this.getGame().getShapeRenderer().setColor(this.color, this.color, this.color, 1.0f);
        this.getGame().getShapeRenderer().point((int) x, (int) y, 0);

        this.getGame().getShapeRenderer().setColor(this.glowColor, this.glowColor, this.glowColor, 0.7f);
        this.getGame().getShapeRenderer().point((int) x - 1, (int) y, 0);
        this.getGame().getShapeRenderer().point((int) x + 1, (int) y, 0);
        this.getGame().getShapeRenderer().point((int) x, (int) y - 1, 0);
        this.getGame().getShapeRenderer().point((int) x, (int) y + 1, 0);
    }

    private boolean isOutOfScreenReset() {
        return y < 0;
    }

    private void reset() {
        y = this.getGame().getViewPort().getHeight() + 50;
    }
}