package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.GameComponent;
import com.nukedbit.shootitout.components.Player;
import com.nukedbit.shootitout.components.StarLayer;
import com.nukedbit.core.components.input.KeyboardInput;
import com.nukedbit.core.graphics.Drawable;
import com.nukedbit.core.graphics.GraphicsAdapter;
import com.nukedbit.core.graphics.ViewPort;
import com.nukedbit.core.utils.Randomize;

import java.util.ArrayList;

public class ShootItOut extends ApplicationAdapter implements Game, GameComponent, Drawable {
    private GraphicsAdapter graphicsAdapter;

    public ShootItOut() {
    }

    private ArrayList<GameComponent> components = new ArrayList<>();

    public ViewPort getViewPort() {
        return new ViewPort(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void create() {
        this.graphicsAdapter = new GraphicsAdapter(Gdx.gl,
                                                   new ShapeRenderer(),
                                                   new SpriteBatch());
        prepareComponents();
        initialize(graphicsAdapter);
    }

    private void prepareComponents() {
        Randomize random = new Randomize();
        KeyboardInput input = new KeyboardInput(this, Gdx.input);
        Player player = new Player(this, "player.png", 80, 80, 100, 100, 20);
        input.subscribe(player);
        this.components.add(input);
        this.components.add(player);
        this.components.add(new StarLayer(this, 500, 50, random));
        this.components.add(new StarLayer(this, 500, 36, random));
    }

    @Override
    public void render() {
        float delta = Math.min(getDeltaTime(), 1 / 60f) * 2;

        update(delta);

        graphicsAdapter.getGl20().glClearColor(0, 0, 0, 1);
        graphicsAdapter.getGl20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        render(graphicsAdapter);
    }

    public void update(float delta) {
        for (GameComponent component : this.components) {
            component.update(delta);
        }
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : this.components) {
            component.initialize(graphicsAdapter);
        }
    }

    @Override
    public ArrayList<GameComponent> getComponents() {
        return this.components;
    }

    @Override
    public Game getGame() {
        return this;
    }

    @Override
    public void render(GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : this.components) {
            if (component.getClass().isInstance(Drawable.class)) {
                ((Drawable)component).render(graphicsAdapter);
            }
        }
    }
}