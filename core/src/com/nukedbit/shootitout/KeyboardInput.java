package com.nukedbit.shootitout;

import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class KeyboardInput implements SceneComponent {
    private final Input input;

    private final int[] lookup = new int[] {
            Input.Keys.LEFT,
            Input.Keys.RIGHT
    };

    public KeyboardInput(Input input) {
        this.input = input;
    }

    @Override
    public void update(float delta, GraphicsAdapter graphicsAdapter) {
        ArrayList<KeyPressed> stream = new ArrayList<>();

        for (int key : lookup) {
            if (input.isKeyPressed(key)) {
                stream.add(new KeyPressed(key));
            }
        }
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {

    }
}