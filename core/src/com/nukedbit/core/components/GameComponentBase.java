package com.nukedbit.core.components;

import java.util.ArrayList;

public abstract class GameComponentBase implements GameComponent {
    private final GameBase game;

    protected GameComponentBase(GameBase game) {
        this.game = game;
    }

    private ArrayList<GameComponent> components = new ArrayList<>();

    public ArrayList<GameComponent> getComponents() {
        return this.components;
    }

    public void update(float dt) {
        for (GameComponent component : components) {
            component.update(dt);
        }
    }

    public void initialize() {
        for (GameComponent component : components) {
            component.initialize();
        }
    }

    public GameBase getGame() {
        return this.game;
    }
}