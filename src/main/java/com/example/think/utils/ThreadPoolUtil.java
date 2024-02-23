package com.example.think.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 运行池
 */
@Slf4j
public class ThreadPoolUtil {
    /**
     * 虚拟线程池
     */
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();


    public static void submitScheduled(Runnable task) {
        executor.submit(task);
    }

    public static void sleep() {
        sleep(10);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error("报错了", e);
        }
    }
}
