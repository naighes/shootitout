package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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

        this.game.initialize();
    }

    @Override
    public void render() {
        super.render();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f) * 2;
        this.game.update(dt);
        this.game.render();
    }
}