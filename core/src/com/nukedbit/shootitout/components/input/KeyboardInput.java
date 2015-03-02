package com.nukedbit.shootitout.components.input;

import com.badlogic.gdx.Input;
import com.nukedbit.shootitout.graphics.GraphicsAdapter;
import com.nukedbit.shootitout.observing.Observable;
import com.nukedbit.shootitout.observing.Observer;
import com.nukedbit.shootitout.components.SceneComponent;

import java.util.ArrayList;

public class KeyboardInput implements SceneComponent, Observable<KeyboardInput.KeyPressed> {
    private final Input input;
    private ArrayList<Observer<KeyPressed>> observers = new ArrayList<>();

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
                stream.add(new KeyPressed(key, delta));
            }
        }

        notify(stream);
    }

    private void notify(ArrayList<KeyPressed> stream) {
        for (KeyPressed key : stream) {
            for (Observer<KeyPressed> observer : observers) {
                observer.notify(key);
            }
        }
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {
    }

    public void subscribe(Observer<KeyPressed> observer) {
        this.observers.add(observer);
    }

    public static class KeyPressed {
        private final int key;
        private final float delta;

        public KeyPressed(int key, float delta) {
            this.key = key;
            this.delta = delta;
        }

        @Override
        public boolean equals(Object other) {
            boolean result = false;

            if (other instanceof KeyPressed) {
                KeyPressed that = (KeyPressed) other;
                result = this.getKey() == that.getKey();
            }

            return result;
        }

        public int getKey() {
            return key;
        }

        public float getDelta() {
            return delta;
        }
    }
}