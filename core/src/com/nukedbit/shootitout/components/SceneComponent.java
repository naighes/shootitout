package com.nukedbit.shootitout.components;

import com.nukedbit.shootitout.graphics.GraphicsAdapter;

public interface SceneComponent {
    void update(float delta, GraphicsAdapter graphicsAdapter);
    void initialize(GraphicsAdapter graphicsAdapter);
}