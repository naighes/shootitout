package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShootItOut extends ApplicationAdapter {
    private ShapeRenderer renderer;

    private final SceneComponent[] components = new SceneComponent[]{
            new StarLayer(500, 50, new Randomize()),
            new StarLayer(500, 36, new Randomize()),
    };

    @Override
    public void create() {
        renderer = new ShapeRenderer();

        for (SceneComponent component : this.components){
            component.initialize();
        }
    }

    @Override
    public void render() {
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f) * 2;

        for (SceneComponent component : this.components){
            component.update(dt);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (SceneComponent component : this.components) {
            component.render(renderer);
        }
    }
}