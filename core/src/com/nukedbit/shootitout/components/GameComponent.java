package com.nukedbit.shootitout.components;

import com.nukedbit.shootitout.Game;
import com.nukedbit.shootitout.graphics.GraphicsAdapter;

import java.util.ArrayList;

public interface GameComponent {
    void update(float delta, GraphicsAdapter graphicsAdapter);
    void initialize(GraphicsAdapter graphicsAdapter);
    ArrayList<GameComponent> getComponents();
    Game getGame();
}