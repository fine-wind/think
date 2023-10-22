package com.example.think.utils;

import java.util.Random;

public class NumberUtil {
    private static final Random r = new Random();

    public static int randomNearby(int spacing) {
        return r.nextInt(spacing) - spacing / 2;
    }
}
