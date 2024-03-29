package com.example.think;

import com.example.think.utils.BrainUtil;
import com.example.think.utils.ThreadPoolUtil;
import com.example.think.utils.WindowFrame;
import com.example.think.v1.form.Entity;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 沉思者
 */
@Slf4j
public class ThinkApplication {

    public static void main(String[] args) {
        final Entity entity = BrainUtil.load();
        /* 开始长脑子吧*/
        if (Objects.isNull(entity)) {
            throw new RuntimeException("启动失败");
        }
        entity.init();
        /* 初始化窗口*/
        new WindowFrame();
        new Thread(entity).start();
        log.info("启动成功");
        ThreadPoolUtil.submitScheduled(BrainUtil.log(entity));/* 定时日志*/
        ThreadPoolUtil.submitScheduled(BrainUtil.saveBrain(entity)); // 保存镜像
    }
}
