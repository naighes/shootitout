package com.nukedbit.shootitout;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public abstract class SceneComponentBase {
    protected ArrayList<SceneComponentBase> components = new ArrayList<>();

    public void render(GraphicsAdapter graphicsAdapter) {
        for (SceneComponentBase component : components) {
            component.render(graphicsAdapter);
        }
    }

    public void update(float delta, GraphicsAdapter graphicsAdapter) {
        for (SceneComponentBase component : components) {
            component.update(delta, graphicsAdapter);
        }
    }

    public void initialize(GraphicsAdapter graphicsAdapter) {
        for (SceneComponentBase component : components) {
            component.initialize(graphicsAdapter);
        }
    }
}