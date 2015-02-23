package com.nukedbit.shootitout;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Renderer{

    void begin(ShapeRenderer.ShapeType shapeType);

    void end();
}

