package com.nukedbit.core.components.cameras;

import com.badlogic.gdx.math.Matrix4;
import com.nukedbit.core.components.GameBase;
import com.nukedbit.core.components.GameComponentBase;

public abstract class Camera extends GameComponentBase {
    public Camera(GameBase game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    public abstract Matrix4 getViewProjection();
}