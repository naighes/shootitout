package com.nukedbit.shootitout;

import java.util.Random;

public class Randomize {
    private Random random;

    public Randomize() {
        random = new Random();
    }

    public int Next(int min, int max) {
        return random.nextInt((max - min) + 1) - min;
    }
}