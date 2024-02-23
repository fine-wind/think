package com.example.think.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtil {

    public static String nowStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }

    public static String time(long time) {
        int ms = 1000;
        int s = ms * 60;
        int m = s * 60;
        int h = m * 60;

        StringBuilder str = new StringBuilder();
        str.append(time % (1000)).append("ms");
        long l = time % s / ms;
        long l1 = time % m / s;
        long l2 = time % h / m;

        if (l > 0) str.insert(0, l + "s");
        if (l1 > 0) str.insert(0, l1 + "m");
        if (l2 > 0) str.insert(0, l2 + "h");
        return str.toString();
    }
}
