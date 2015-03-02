package com.nukedbit.shootitout.components;

import com.nukedbit.shootitout.Game;
import com.nukedbit.shootitout.graphics.Drawable;
import com.nukedbit.shootitout.graphics.GraphicsAdapter;

public class DrawableComponentBase extends GameComponentBase implements Drawable {
    protected DrawableComponentBase(Game game) {
        super(game);
    }

    public void render(GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : this.getComponents()) {
            if (component.getClass().isInstance(Drawable.class)) {
                ((Drawable) component).render(graphicsAdapter);
            }
        }
    }
}