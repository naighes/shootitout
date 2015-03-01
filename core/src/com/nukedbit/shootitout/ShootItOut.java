package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShootItOut extends ApplicationAdapter {
    private GraphicsAdapter graphicsAdapter;

    public ShootItOut() {
    }

    private SceneComponent[] components;

    @Override
    public void create() {
        this.graphicsAdapter = new GraphicsAdapter(Gdx.gl,
                                                   Gdx.graphics,
                                                   new ShapeRenderer(),
                                                   new SpriteBatch());

        components = new SceneComponent[] {
                new KeyboardInput(Gdx.input),
                new StarLayer(500, 50, new Randomize()),
                new StarLayer(500, 36, new Randomize()),
                new Player("player.png", 80, 80, 100, 100, 20)
        };

        for (SceneComponent component : this.components) {
            component.initialize(graphicsAdapter);
        }
    }

    @Override
    public void render() {
        float delta = Math.min(graphicsAdapter.getGraphics().getDeltaTime(), 1 / 60f) * 2;

        update(delta);

        graphicsAdapter.getGl20().glClearColor(0, 0, 0, 1);
        graphicsAdapter.getGl20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (SceneComponent component : this.components) {
            if (component.getClass().isInstance(Drawable.class)) {
                ((Drawable)component).render(graphicsAdapter);
            }
        }
    }

    private void update(float delta) {
        for (SceneComponent component : this.components) {
            component.update(delta, graphicsAdapter);
        }
    }
}