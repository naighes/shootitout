package com.nukedbit.shootitout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class StarLayer extends SceneComponentBase {
    private final int starsCount;
    private final int starSpeed;
    private final Randomize random;

    public StarLayer(int starsCount, int starSpeed, Randomize random) {
        this.starsCount = starsCount;
        this.starSpeed = starSpeed;
        this.random = random;
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {
        for (int i = 0; i < starsCount; i++) {
            components.add(Star.create(starSpeed, random, graphicsAdapter.getGraphics()));
        }

        super.initialize(graphicsAdapter);
    }

    @Override
    public void update(float delta, GraphicsAdapter graphicsAdapter) {
        super.update(delta, graphicsAdapter);
    }

    @Override
    public void render(GraphicsAdapter graphicsAdapter) {
        graphicsAdapter.getRenderer().begin(ShapeRenderer.ShapeType.Point);
        graphicsAdapter.getRenderer().setColor(Color.WHITE);

        super.render(graphicsAdapter);

        graphicsAdapter.getRenderer().end();
    }
}