package com.example.think.utils;

import com.example.think.config.Configs;
import com.example.think.v1.form.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 窗口
 */
public class WindowFrame extends JFrame {

    public WindowFrame() throws HeadlessException {
        this.setTitle("think");
        /* 输入框的高度*/
        int height = 150;
        TextArea comp1 = new TextArea();
        {
            comp1.setBounds(0, 0, Configs.WINDOW_WIDE, Configs.WINDOW_HIGH - height);
            comp1.setEditable(false);
            this.add(comp1);
        }
        {
            TextArea comp = new TextArea("", 2, 2);
            comp.setBounds(0, Configs.WINDOW_HIGH - height, Configs.WINDOW_WIDE, height);
            comp.requestFocusInWindow();
            comp.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        // 处理按下shift+回车键的逻辑
                        String text = comp.getText();
                        comp1.append("\n输入：" + text);
                        ThreadPoolUtil.submitScheduled(() -> {
                            String msg = Entity.connect(text);
                            comp1.append("\n输出：" + msg);
                        });
                        comp.setText(null);
                    }
                }
            });
            this.add(comp);
        }
        // 设置窗口的大小和位置
        this.setSize(Configs.WINDOW_WIDE, Configs.WINDOW_HIGH);

        // 设置窗体关闭操作
        // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 居中显示
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        // 显示窗体
        this.setVisible(true);
    }
}
