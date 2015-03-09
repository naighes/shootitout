package com.nukedbit.framework.components.cameras;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponentBase;

public abstract class Camera extends GameComponentBase {
    protected Vector2 position;

    public Camera(GameBase game, Vector2 initialPosition) {
        super(game);

        this.position = initialPosition;
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

    public void setPosition(Vector2 value) {
        this.position = value;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }
}