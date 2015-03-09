package com.nukedbit.framework.utils;

import java.util.Random;

public class Randomize {
    private Random random;

    public Randomize() {
        random = new Random();
    }

    public int next(int min, int max) {
        return random.nextInt((max - min) + 1) - min;
    }

    public float next() {
        return random.nextFloat();
    }
}