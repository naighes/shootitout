package com.nukedbit.shootitout;

import java.util.ArrayList;

public abstract class SceneComponentBase implements SceneComponent, Drawable {
    protected ArrayList<SceneComponent> components = new ArrayList<>();

    public void render(GraphicsAdapter graphicsAdapter) {
        for (SceneComponent component : components) {
            if (component.getClass().isInstance(Drawable.class)) {
                ((Drawable) component).render(graphicsAdapter);
            }
        }
    }

    public void update(float delta, GraphicsAdapter graphicsAdapter) {
        for (SceneComponent component : components) {
            component.update(delta, graphicsAdapter);
        }
    }

    public void initialize(GraphicsAdapter graphicsAdapter) {
        for (SceneComponent component : components) {
            component.initialize(graphicsAdapter);
        }
    }
}