package com.nukedbit.framework.physics;

import com.badlogic.gdx.math.Vector3;

public class RigidBody {
    private final float mass;
    private final Vector3 direction;
    private Vector3 velocity;
    private final Environment environment;

    private float scalarForce;

    public RigidBody(Vector3 direction,
                     Vector3 initialVelocity,
                     float mass,
                     float scalarForce,
                     Environment environment) {
        this.mass = mass;
        this.direction = direction;
        this.scalarForce = scalarForce;
        this.velocity = initialVelocity;
        this.environment = environment;
    }

    public void setDirection(float x, float y, float z) {
        this.direction.x = x;
        this.direction.y = y;
        this.direction.z = z;
    }

    public Vector3 getDirection() {
        return this.direction;
    }

    public void setScalarForce(float value) {
        this.scalarForce = value;
    }

    public void update(float dt) {
        this.velocity.add(calculateVelocity(this.calculateAcceleration(), dt));
        this.velocity.add(this.environment.calculateDragVelocity(this.velocity));
    }

    private Vector3 calculateAcceleration() {
        return new Vector3(this.direction.x * this.scalarForce / this.mass,
                           this.direction.y * this.scalarForce / this.mass,
                           this.direction.z * this.scalarForce / this.mass);
    }

    private Vector3 calculateVelocity(Vector3 acceleration, float dt) {
        return new Vector3((0.5f) * (acceleration.x * (float) Math.pow(dt, 2f)),
                           (0.5f) * (acceleration.y * (float) Math.pow(dt, 2f)),
                           (0.5f) * (acceleration.z * (float) Math.pow(dt, 2f)));
    }

    public Vector3 getVelocity() {
        return this.velocity;
    }
}