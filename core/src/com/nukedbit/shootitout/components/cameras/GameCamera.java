package com.nukedbit.shootitout.components.cameras;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.cameras.CameraBase;
import com.nukedbit.framework.graphics.ViewPort;

public class GameCamera extends CameraBase {
    private com.badlogic.gdx.graphics.Camera innerCamera;

    public GameCamera(GameBase game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        ViewPort viewPort = this.getGame().getViewPort();
        this.innerCamera = new PerspectiveCamera(67, viewPort.getWidth(), viewPort.getHeight());
        this.innerCamera.position.set(0, 0f, 0);
        this.innerCamera.lookAt(0f, 0f, -1f);
        this.innerCamera.near = 0.1f;
        this.innerCamera.far = 100f;
        this.innerCamera.update();
    }

    @Override
    public Matrix4 getViewProjection() {
        return this.getInnerCamera().combined;
    }

    @Override
    public com.badlogic.gdx.graphics.Camera getInnerCamera() {
        return this.innerCamera;
    }
}