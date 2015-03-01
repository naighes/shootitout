package com.nukedbit.shootitout;

public class KeyPressed {
    private final int key;

    public KeyPressed(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;

        if (other instanceof KeyPressed) {
            KeyPressed that = (KeyPressed) other;
            result = this.key == that.key;
        }

        return result;
    }
}