package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nukedbit.shootitout.components.Player;
import com.nukedbit.shootitout.components.SceneComponent;
import com.nukedbit.shootitout.components.StarLayer;
import com.nukedbit.shootitout.components.input.KeyboardInput;
import com.nukedbit.shootitout.graphics.Drawable;
import com.nukedbit.shootitout.graphics.GraphicsAdapter;
import com.nukedbit.shootitout.utils.Randomize;

import java.util.ArrayList;

public class ShootItOut extends ApplicationAdapter {
    private GraphicsAdapter graphicsAdapter;

    public ShootItOut() {
    }

    private ArrayList<SceneComponent> components = new ArrayList<>();

    @Override
    public void create() {
        this.graphicsAdapter = new GraphicsAdapter(Gdx.gl,
                                                   Gdx.graphics,
                                                   new ShapeRenderer(),
                                                   new SpriteBatch());
        prepareComponents();

        for (SceneComponent component : this.components) {
            component.initialize(graphicsAdapter);
        }
    }

    private void prepareComponents() {
        Randomize random = new Randomize();
        KeyboardInput input = new KeyboardInput(Gdx.input);
        Player player = new Player("player.png", 80, 80, 100, 100, 20);
        input.subscribe(player);
        this.components.add(input);
        this.components.add(player);
        this.components.add(new StarLayer(500, 50, random));
        this.components.add(new StarLayer(500, 36, random));
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