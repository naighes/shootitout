package com.nukedbit.framework.components.cameras;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponentBase;
import com.nukedbit.framework.physics.WorldObject;

public abstract class CameraBase extends GameComponentBase implements WorldObject {
    public CameraBase(GameBase game) {
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

    public void setPosition(Vector3 value) {
        this.getInnerCamera().position.set(value.x,
                                           value.y,
                                           value.z);
    }

    public void setPosition(float x, float y, float z) {
        this.getInnerCamera().position.set(x, y, z);
    }

    public abstract com.badlogic.gdx.graphics.Camera getInnerCamera();

    public Vector3 getPosition() {
        return new Vector3(this.getInnerCamera().position.x,
                           this.getInnerCamera().position.y,
                           this.getInnerCamera().position.z);
    }
}