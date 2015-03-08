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
        for (int i = 0; i < this.getComponents().size(); i++) {
            this.getComponents().get(i).update(dt);
        }
    }

    public void initialize() {
        for (int i = 0; i < this.getComponents().size(); i++) {
            this.getComponents().get(i).initialize();
        }
    }

    public GameBase getGame() {
        return this.game;
    }
}