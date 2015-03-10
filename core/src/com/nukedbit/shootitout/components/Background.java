package com.nukedbit.shootitout.components;

import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.SpriteComponent;
import com.nukedbit.framework.physics.WorldObject;

public class Background extends SpriteComponent implements WorldObject {
    private float speed = 10f;

    private Vector2 position;

    protected Background(GameBase game, String texturePath) {
        super(game, texturePath);
    }

    @Override
    public void initialize() {
        super.initialize();

        this.position = new Vector2(this.getGame().getViewPort().getWidth()/2 * -1f,
                                    this.getGame().getViewPort().getHeight()/2 * -1f);
        this.innerSprite.setPosition(this.position.x, this.position.y);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.position.y = this.position.y - this.speed * dt;
        this.innerSprite.setPosition(this.position.x, this.position.y);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }
}