package com.nukedbit.framework.components;

import java.util.ArrayList;

public abstract class GameComponentBase implements GameComponent {
    private final GameBase game;
    private boolean initialized = false;

    protected GameComponentBase(GameBase game) {
        this.game = game;
    }

    private ArrayList<GameComponent> components = new ArrayList<>();

    public ArrayList<GameComponent> getComponents() {
        return this.components;
    }

    public void update(float dt) {
        this.ensureInitialization();

        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);
            component.update(dt);
        }
    }

    protected void ensureInitialization() {
        if (!this.initialized)
            this.initialize();
    }

    public void initialize() {
        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);
            component.initialize();
        }

        this.initialized = true;
    }

    public GameBase getGame() {
        return this.game;
    }
}