package com.nukedbit.core.physics;

import com.badlogic.gdx.math.Vector2;

public class Environment {
    private final float drag;

    public Environment(float drag) {
        this.drag = drag;
    }

    public Vector2 calculateDragVelocity(Vector2 currentVelocity) {
        return new Vector2(currentVelocity.x * -1f * this.drag,
                           currentVelocity.y * -1f * this.drag);
    }
}