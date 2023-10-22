package com.example.think.utils;

import java.util.Random;

public class RandomUtil {
    private static final Random r = new Random();

    public static boolean nextBoolean() {
        return r.nextBoolean();
    }

    public static int nextCellId(int size) {
        while (true) {
            int i = r.nextInt(size);
            if (i > 0 && i < size) {
                return i;
            }
        }
    }
}
