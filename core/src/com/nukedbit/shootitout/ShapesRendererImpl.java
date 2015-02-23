package com.nukedbit.shootitout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by sebastian on 23/02/15.
 */
public class ShapesRendererImpl implements ShapesRenderer {

    private final ShapeRenderer shapeRenderer;

    public ShapesRendererImpl() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void setColor(Color color) {
        shapeRenderer.setColor(color);
    }

    @Override
    public void point(int x, int y, int z) {
        shapeRenderer.point(x, y, z);
    }

    @Override
    public void begin(ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.begin(shapeType);
    }

    @Override
    public void end() {
        shapeRenderer.end();
    }
}
