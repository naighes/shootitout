package com.nukedbit.framework.physics;

import com.badlogic.gdx.math.Vector3;

public class Environment {
    private final float drag;

    public Environment(float drag) {
        this.drag = drag;
    }

    public Vector3 calculateDragVelocity(Vector3 currentVelocity) {
        return new Vector3(currentVelocity.x * -1f * this.drag,
                           currentVelocity.y * -1f * this.drag,
                           currentVelocity.z * -1f * this.drag);
    }
}