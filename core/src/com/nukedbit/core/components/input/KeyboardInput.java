package com.nukedbit.core.components.input;

import com.badlogic.gdx.Input;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.GameComponentBase;
import com.nukedbit.core.observing.Observable;
import com.nukedbit.core.observing.Observer;

import java.util.ArrayList;
import java.util.HashSet;

public class KeyboardInput extends GameComponentBase implements Observable<KeyboardInput.KeyEvent> {
    private final Input input;
    private ArrayList<Observer<KeyEvent>> observers = new ArrayList<>();

    private final int[] lookup;

    public KeyboardInput(Game game, Input input, int[] lookup) {
        super(game);

        this.input = input;
        this.lookup = lookup;
    }

    private final HashSet<Integer> pressed = new HashSet<>();

    @Override
    public void update(float dt) {
        super.update(dt);

        for (int key : lookup) {
            if (input.isKeyPressed(key) && !pressed.contains(key)) {
                pressed.add(key);
                notify(new KeyPressed(key, dt));
            }

            if (!input.isKeyPressed(key) && pressed.contains(key)) {
                pressed.remove(key);
                notify(new KeyReleased(key, dt));
            }
        }
    }

    private void notify(KeyEvent event) {
        for (Observer<KeyEvent> observer : observers) {
            observer.notify(event);
        }
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public void subscribe(Observer<KeyEvent> observer) {
        this.observers.add(observer);
    }

    public abstract static class KeyEvent {
        private final int key;
        private final float delta;

        public KeyEvent(int key, float delta) {
            this.key = key;
            this.delta = delta;
        }

        @Override
        public boolean equals(Object other) {
            boolean result = false;

            if (other instanceof KeyPressed) {
                KeyEvent that = (KeyEvent) other;
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

    public static class KeyPressed extends KeyEvent {
        public KeyPressed(int key, float delta) {
            super(key, delta);
        }

        @Override
        public boolean equals(Object other) {
            return super.equals(other) && other instanceof KeyPressed;
        }
    }

    public static class KeyReleased extends KeyEvent {
        public KeyReleased(int key, float delta) {
            super(key, delta);
        }

        @Override
        public boolean equals(Object other) {
            return super.equals(other) && other instanceof KeyReleased;
        }
    }
}