package com.example.think.utils;

import com.example.think.v0.config.Configs;
import com.example.think.v1.form.Entity;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class WindowFrame extends JFrame {

    @Getter
    private final MyCanvas canvas;

    public WindowFrame(Entity entity) throws HeadlessException {
        setTitle("think");
        // 创建画布对象
        canvas = new MyCanvas(entity);
        canvas.setWindowFrame(this);
        // 将画布添加到窗体中
        add(canvas);
        // 设置窗口的大小和位置
        int windowLength = Configs.WINDOW_LENGTH;
        setSize(windowLength, windowLength);

        // 设置窗体关闭操作
        // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 居中显示
        setLocationRelativeTo(null);
        setResizable(false);
        // 显示窗体
        setVisible(true);
        Thread.startVirtualThread(canvas);
    }
}
