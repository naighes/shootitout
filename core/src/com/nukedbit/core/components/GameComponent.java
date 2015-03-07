package com.nukedbit.core.components;

import com.nukedbit.core.Game;

import java.util.ArrayList;

public interface GameComponent {
    void update(float delta);
    void initialize();
    ArrayList<GameComponent> getComponents();
    Game getGame();
}