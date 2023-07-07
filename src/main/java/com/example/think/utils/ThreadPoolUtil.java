package com.example.think.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolUtil {

    static ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 100, 1L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public static void scheduleAtFixedRate(Runnable runnable) {
        int size = ((ThreadPoolExecutor) executorService).getQueue().size();
        log.trace("当前排队个数 {}", size);
        executorService.submit(runnable);
    }
}
