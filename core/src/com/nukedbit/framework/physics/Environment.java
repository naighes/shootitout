package com.nukedbit.framework.physics;

import com.badlogic.gdx.math.Vector3;

public class Environment {
    private final float drag;
    private final com.badlogic.gdx.graphics.g3d.Environment environment;

    public Environment(float drag,
                       com.badlogic.gdx.graphics.g3d.Environment environment) {
        this.drag = drag;
        this.environment = environment;
    }

    public com.badlogic.gdx.graphics.g3d.Environment getInnerEnvironment() {
        return this.environment;
    }

    public Vector3 calculateDragVelocity(Vector3 currentVelocity) {
        return new Vector3(currentVelocity.x * -1f * this.drag,
                           currentVelocity.y * -1f * this.drag,
                           currentVelocity.z * -1f * this.drag);
    }
}