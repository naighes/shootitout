package com.nukedbit.framework.components;

import com.nukedbit.framework.graphics.Drawable;

public class DrawableComponentBase extends GameComponentBase implements Drawable {
    protected DrawableComponentBase(GameBase game) {
        super(game);
    }

    public void render() {
        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);

            if (component instanceof Drawable) {
                ((Drawable) component).render();
            }
        }
    }
}