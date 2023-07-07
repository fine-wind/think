package com.example.think.utils;

import com.example.think.config.Configs;

import java.io.File;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.example.think.config.Configs.LOG.*;

public class NumberUtil {
    private static final Random r = new Random();

    public static int randomNearby(int spacing) {
        return r.nextInt(spacing) - spacing / 2;
    }
}
