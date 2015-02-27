package com.nukedbit.shootitout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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
    public void initialize() {
        for (int i = 0; i < starsCount; i++) {
            components.add(Star.create(starSpeed, random, Gdx.graphics));
        }

        super.initialize();
    }

    @Override
    public void update(float delta, Graphics graphics) {
        super.update(delta, graphics);
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Point);
        renderer.setColor(Color.WHITE);

        super.render(renderer);

        renderer.end();
    }
}