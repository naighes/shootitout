package com.nukedbit.shootitout.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.DrawableComponentBase;
import com.nukedbit.core.graphics.GraphicsAdapter;
import com.nukedbit.core.utils.Randomize;

public class StarLayer extends DrawableComponentBase {
    private final int starsCount;
    private final int starSpeed;
    private final Randomize random;

    public StarLayer(Game game,
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
    public void render(GraphicsAdapter graphicsAdapter) {
        graphicsAdapter.getRenderer().begin(ShapeRenderer.ShapeType.Point);
        graphicsAdapter.getRenderer().setColor(Color.WHITE);

        super.render(graphicsAdapter);

        graphicsAdapter.getRenderer().end();
    }
}