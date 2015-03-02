package com.nukedbit.shootitout.components;

import com.nukedbit.shootitout.Game;
import com.nukedbit.shootitout.graphics.GraphicsAdapter;

import java.util.ArrayList;

public abstract class GameComponentBase implements GameComponent {
    private final Game game;

    protected GameComponentBase(Game game) {
        this.game = game;
    }

    private ArrayList<GameComponent> components = new ArrayList<>();

    public ArrayList<GameComponent> getComponents() {
        return this.components;
    }

    public void update(float delta, GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : components) {
            component.update(delta, graphicsAdapter);
        }
    }

    public void initialize(GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : components) {
            component.initialize(graphicsAdapter);
        }
    }

    public Game getGame() {
        return this.game;
    }
}