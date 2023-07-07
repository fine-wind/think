package com.example.think;

import com.example.think.world.Brain;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThinkApplication {

    public static void main(String[] args) {
        /* 呵呵，开始长脑子了*/
        new Thread(new Brain()).start();
        log.info("启动成功");
    }
}
