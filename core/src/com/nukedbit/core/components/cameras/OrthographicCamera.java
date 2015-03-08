package com.nukedbit.core.components.cameras;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.components.GameBase;

public class OrthographicCamera extends com.nukedbit.core.components.cameras.Camera {
    private final Vector2 position = new Vector2(0f, 0f);

    private com.badlogic.gdx.graphics.OrthographicCamera innerCamera;

    public OrthographicCamera(GameBase game, Vector2 initialPosition) {
        super(game, initialPosition);
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
        this.innerCamera.translate(this.position.x, this.position.y, 0f);
        this.innerCamera.zoom = 5.8f;
        this.innerCamera.update();
    }

    @Override
    public Matrix4 getViewProjection() {
        return this.innerCamera.combined;
    }
}