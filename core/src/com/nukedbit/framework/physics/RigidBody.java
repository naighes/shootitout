package com.nukedbit.framework.physics;

import com.badlogic.gdx.math.Vector2;

public class RigidBody {
    private final float mass;
    private final Vector2 direction;
    private Vector2 velocity;
    private final Environment environment;

    private float scalarForce;

    public RigidBody(Vector2 direction,
                     Vector2 initialVelocity,
                     float mass,
                     float scalarForce,
                     Environment environment) {
        this.mass = mass;
        this.direction = direction;
        this.scalarForce = scalarForce;
        this.velocity = initialVelocity;
        this.environment = environment;
    }

    public void setDirection(float x, float y) {
        this.direction.x = x;
        this.direction.y = y;
    }

    public Vector2 getDirection() {
        return this.direction;
    }

    public void setScalarForce(float value) {
        this.scalarForce = value;
    }

    public void update(float dt) {
        this.velocity.add(calculateVelocity(calculateAcceleration(), dt));
        this.velocity.add(this.environment.calculateDragVelocity(this.velocity));
    }

    private Vector2 calculateAcceleration() {
        return new Vector2(this.direction.x * this.scalarForce / this.mass,
                           this.direction.y * this.scalarForce / this.mass);
    }

    private Vector2 calculateVelocity(Vector2 acceleration, float dt) {
        return new Vector2((0.5f) * (acceleration.x * (float) Math.pow(dt, 2f)),
                           (0.5f) * (acceleration.y * (float) Math.pow(dt, 2f)));
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }
}