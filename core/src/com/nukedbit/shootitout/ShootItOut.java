package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.shootitout.components.MainGame;

public class ShootItOut extends ApplicationAdapter {
    private GameBase game;

    public ShootItOut() {
    }

    @Override
    public void create() {
        super.create();

        this.game = new MainGame(Gdx.graphics,
                                 Gdx.gl20,
                                 new SpriteBatch(),
                                 new ShapeRenderer());

        game.initialize();
    }

    @Override
    public void render() {
        super.render();

        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f) * 2;
        this.game.update(dt);
        this.game.render();
    }
}