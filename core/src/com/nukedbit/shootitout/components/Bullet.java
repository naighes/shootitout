package com.nukedbit.shootitout.components;

import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.Sprite;
import com.nukedbit.framework.physics.WorldObject;

public class Bullet extends Sprite implements WorldObject {
    private Vector2 position;

    public Bullet(GameBase game, Vector2 initialPosition) {
        super(game, "laser.png");

        this.position = initialPosition;
    }

    @Override
    public void initialize() {
        super.initialize();

        this.innerSprite.setSize(this.innerSprite.getWidth() * 0.2f,
                                 this.innerSprite.getHeight() * 0.2f);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.position.add(0f, 9.8f);
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