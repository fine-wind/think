package com.example.think.config;

import java.util.logging.Level;

public interface Configs {
    /**
     * 神经元数量
     */
    int callNumber = 5000;

    int WINDOW_WIDTH = 800;

    int WINDOW_HEIGHT = 600;

    int ELE_SIZE = 5;

    interface LOG {
        // 设置日志级别为INFO
        Level level = Level.FINE;
        // 最大文件大小为 1MB
        int maxFileSize = 1024 * 1024;
        // 备份文件个数为 10
        int maxBackupIndex = 10;
    }
}
