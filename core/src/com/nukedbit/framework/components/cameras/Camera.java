package com.nukedbit.framework.components.cameras;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponentBase;
import com.nukedbit.framework.physics.WorldObject;

public abstract class Camera extends GameComponentBase implements WorldObject {
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

    public void setPosition(Vector2 value) {
        this.getInnerCamera().position.set(value.x, value.y, this.getInnerCamera().position.z);
    }

    public void setPosition(float x, float y) {
        this.getInnerCamera().position.set(x, y, this.getInnerCamera().position.z);
    }

    public abstract com.badlogic.gdx.graphics.Camera getInnerCamera();

    public Vector2 getPosition() {
        return new Vector2(this.getInnerCamera().position.x, this.getInnerCamera().position.y);
    }
}