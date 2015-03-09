package com.nukedbit.shootitout.components;

import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.AnimatedSprite;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.physics.WorldObject;

public class Bullet extends AnimatedSprite implements WorldObject {
    private Vector2 position;

    public Bullet(GameBase game, Vector2 initialPosition) {
        super(game, "bullet.png", 1, 6, 0.010f);

        this.position = initialPosition;

    }

    @Override
    public void initialize() {
        super.initialize();

        this.innerSprite.setSize(16 * 0.6f,
                57 * 0.6f);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.position.add(0f, 8.8f);
        this.innerSprite.setPosition(this.position.x, this.position.y);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }
}