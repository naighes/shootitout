package com.nukedbit.framework.components.cameras;

import com.badlogic.gdx.math.Matrix4;
import com.nukedbit.framework.components.GameBase;

public class OrthographicCamera extends CameraBase {
    private com.badlogic.gdx.graphics.OrthographicCamera innerCamera;

    public OrthographicCamera(GameBase game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        int width = this.getGame().getViewPort().getWidth();
        int height = this.getGame().getViewPort().getHeight();
        this.innerCamera = new com.badlogic.gdx.graphics.OrthographicCamera(width, height);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public Matrix4 getViewProjection() {
        return this.getInnerCamera().combined;
    }

    @Override
    public com.badlogic.gdx.graphics.OrthographicCamera getInnerCamera() {
        return this.innerCamera;
    }
}