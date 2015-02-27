package com.nukedbit.shootitout;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public abstract class SceneComponentBase {
    protected ArrayList<SceneComponentBase> components = new ArrayList<SceneComponentBase>();

    public void render(ShapeRenderer renderer){
        for (SceneComponentBase component : components){
            component.render(renderer);
        }
    }

    public void update(float delta){
        for (SceneComponentBase component : components){
            component.update(delta);
        }
    }

    public void initialize() {
        for (SceneComponentBase component : components){
            component.initialize();
        }
    }
}