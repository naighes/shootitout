package com.nukedbit.shootitout;

public class KeyPressed {
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