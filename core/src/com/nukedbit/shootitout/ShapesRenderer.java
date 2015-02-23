package com.nukedbit.shootitout;

import com.badlogic.gdx.graphics.Color;

public interface ShapesRenderer extends Renderer
{
    void setColor(Color color);

    void point(int x, int y, int i);
}
