package com.nukedbit.core.components;

import com.nukedbit.core.Game;
import com.nukedbit.core.graphics.Drawable;
import com.nukedbit.core.graphics.GraphicsAdapter;

public class DrawableComponentBase extends GameComponentBase implements Drawable {
    protected DrawableComponentBase(Game game) {
        super(game);
    }

    public void render(GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : this.getComponents()) {
            if (component instanceof Drawable) {
                ((Drawable) component).render(graphicsAdapter);
            }
        }
    }
}