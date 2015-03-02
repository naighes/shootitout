package com.nukedbit.core.components;

import com.nukedbit.core.Game;
import com.nukedbit.core.graphics.GraphicsAdapter;

import java.util.ArrayList;

public interface GameComponent {
    void update(float delta);
    void initialize(GraphicsAdapter graphicsAdapter);
    ArrayList<GameComponent> getComponents();
    Game getGame();
}