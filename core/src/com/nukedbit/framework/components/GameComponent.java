package com.nukedbit.framework.components;

import java.util.ArrayList;

public interface GameComponent {
    void update(float delta);
    void initialize();
    ArrayList<GameComponent> getComponents();
    GameBase getGame();
}