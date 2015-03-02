package com.nukedbit.core.components.input;

import com.badlogic.gdx.Input;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.GameComponentBase;
import com.nukedbit.core.graphics.GraphicsAdapter;
import com.nukedbit.core.observing.Observable;
import com.nukedbit.core.observing.Observer;

import java.util.ArrayList;

public class KeyboardInput extends GameComponentBase implements Observable<KeyboardInput.KeyPressed> {
    private final Input input;
    private ArrayList<Observer<KeyPressed>> observers = new ArrayList<>();

    private final int[] lookup = new int[] {
            Input.Keys.LEFT,
            Input.Keys.RIGHT,
            Input.Keys.UP,
            Input.Keys.DOWN
    };

    public KeyboardInput(Game game, Input input) {
        super(game);

        this.input = input;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

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
        super.initialize(graphicsAdapter);
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