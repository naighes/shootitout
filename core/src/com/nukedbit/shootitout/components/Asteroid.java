package com.nukedbit.shootitout.components;

import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.SpriteComponent;
import com.nukedbit.framework.physics.WorldObject;

public class Asteroid extends SpriteComponent implements WorldObject {
    private final float scale;

    public Asteroid(GameBase game, Vector2 initialPosition, float scale) {
        super(game, "asteroid_01.png");

        this.position = initialPosition;
        this.scale = scale;
    }

    private Vector2 position;

    @Override
    public void initialize() {
        super.initialize();
    }

    private void setSize(float dt) {
        Vector2 size = this.getSize(dt);
        this.innerSprite.setSize(size.x * this.scale, size.y * this.scale);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.setPosition();
        this.setSize(dt);
    }

    private void setPosition() {
        this.position.y -= 3f;
        this.innerSprite.setPosition(this.position.x, this.position.y);
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }
}