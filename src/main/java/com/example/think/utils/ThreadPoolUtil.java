package com.example.think.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 运行池
 */
@Slf4j
public class ThreadPoolUtil {
    static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    /**
     * 默认等待时长 ms
     */
    private static long sleepTime = 100;

    public static void submitScheduled(Runnable task) {
        executor.submit(task);
    }

    public static void sleep() {
        sleep(100);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ignored) {
        }
        long sleepTime1 = sleepTime + time;
        sleepTime = sleepTime1 >= 3000 ? time : sleepTime1;
    }
}
