package com.nukedbit.shootitout;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface SceneComponent {
    void initialize();
    void update(float delta);
    void render(ShapeRenderer renderer);
}