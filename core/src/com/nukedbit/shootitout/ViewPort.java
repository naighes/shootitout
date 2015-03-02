package com.nukedbit.shootitout;

public class ViewPort {
    private final int width;
    private final int height;

    public ViewPort(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}