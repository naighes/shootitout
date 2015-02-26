package com.nukedbit.shootitout;

import java.util.Random;

/**
 * Created by baldin on 26/02/2015.
 */
public class RandomValue {
    private Random random;
    public RandomValue(){
        random=  new Random();
    }
    public int Next(int min, int max) {
        return random.nextInt((max-min)+1)-min;
    }
}
