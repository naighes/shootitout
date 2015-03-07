package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Graphics;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.GameComponent;
import com.nukedbit.core.graphics.Drawable;
import com.nukedbit.core.graphics.GraphicsAdapter;
import com.nukedbit.core.graphics.ViewPort;

import java.util.ArrayList;

public abstract class GameBase implements Game, GameComponent, Drawable {
    private final Graphics graphics;

    public GameBase(Graphics graphics) {
        this.graphics = graphics;
    }

    private ArrayList<GameComponent> components = new ArrayList<>();

    @Override
    public void render(GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : this.getComponents()) {
            if (component instanceof Drawable) {
                ((Drawable) component).render(graphicsAdapter);
            }
        }
    }

    @Override
    public ViewPort getViewPort() {
        return new ViewPort(this.graphics.getWidth(), this.graphics.getHeight());
    }

    @Override
    public float getDeltaTime() {
        return this.graphics.getDeltaTime();
    }

    @Override
    public void update(float delta) {
        for (GameComponent component : this.getComponents()) {
            component.update(delta);
        }
    }

    @Override
    public void initialize() {
        for (GameComponent component : this.getComponents()) {
            component.initialize();
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
}