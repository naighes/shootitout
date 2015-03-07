package com.nukedbit.core.components.cameras;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.components.GameBase;

public class OrthographicCamera extends com.nukedbit.core.components.cameras.Camera {
    private final Vector2 position;

    private Camera innerCamera;

    public OrthographicCamera(GameBase game, Vector2 initialPosition) {
        super(game);

        this.position = initialPosition;
    }

    @Override
    public void initialize() {
        super.initialize();

        int width = this.getGame().getViewPort().getWidth();
        int height = this.getGame().getViewPort().getHeight();
        this.innerCamera = new com.badlogic.gdx.graphics.OrthographicCamera(width, height);

        this.updatePosition();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.updatePosition();
    }

    private void updatePosition() {
        this.innerCamera.position.x = this.position.x;
        this.innerCamera.position.y = this.position.y;
        this.innerCamera.position.z = 0f;
        this.innerCamera.update();
    }

    @Override
    public Matrix4 getViewProjection() {
        return this.innerCamera.combined;
    }
}