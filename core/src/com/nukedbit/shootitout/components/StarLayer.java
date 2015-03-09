package com.nukedbit.shootitout.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nukedbit.framework.components.DrawableComponentBase;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.utils.Randomize;

public class StarLayer extends DrawableComponentBase {
    private final int starsCount;
    private final int starSpeed;
    private final Randomize random;

    public StarLayer(GameBase game,
                     int starsCount,
                     int starSpeed,
                     Randomize random) {
        super(game);

        this.starsCount = starsCount;
        this.starSpeed = starSpeed;
        this.random = random;
    }

    @Override
    public void initialize() {
        for (int i = 0; i < starsCount; i++) {
            this.getComponents().add(Star.create(this.getGame(),
                                                 starSpeed,
                                                 random));
        }

        super.initialize();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void render() {
        this.getGame().getShapeRenderer().begin(ShapeRenderer.ShapeType.Point);

        super.render();

        this.getGame().getShapeRenderer().end();
    }
}