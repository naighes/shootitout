package com.nukedbit.core.graphics;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// TODO: it's very "weak".
public class GraphicsAdapter {
    public GraphicsAdapter(GL20 gl20,
                           ShapeRenderer renderer,
                           SpriteBatch spriteBatch) {
        this.gl20 = gl20;
        this.renderer = renderer;
        this.spriteBatch = spriteBatch;
    }

    private final GL20 gl20;

    public GL20 getGl20() {
        return gl20;
    }

    private final ShapeRenderer renderer;

    public ShapeRenderer getRenderer() {
        return renderer;
    }

    private SpriteBatch spriteBatch;

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}