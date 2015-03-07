package com.nukedbit.core.components;

import com.nukedbit.core.graphics.Drawable;

public class DrawableComponentBase extends GameComponentBase implements Drawable {
    protected DrawableComponentBase(GameBase game) {
        super(game);
    }

    public void render() {
        for (GameComponent component : this.getComponents()) {
            if (component instanceof Drawable) {
                ((Drawable) component).render();
            }
        }
    }
}