package com.nukedbit.shootitout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public abstract class SceneComponentBase {
    protected ArrayList<SceneComponentBase> components = new ArrayList<>();

    public void render(ShapeRenderer renderer) {
        for (SceneComponentBase component : components) {
            component.render(renderer);
        }
    }

    public void update(float delta, Graphics graphics) {
        for (SceneComponentBase component : components) {
            component.update(delta, graphics);
        }
    }

    public void initialize(Graphics graphics) {
        for (SceneComponentBase component : components) {
            component.initialize(Gdx.graphics);
        }
    }
}