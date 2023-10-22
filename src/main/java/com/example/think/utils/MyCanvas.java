package com.example.think.utils;

import com.example.think.v0.config.Configs;
import com.example.think.v1.form.Entity;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

@Slf4j
public class MyCanvas extends Canvas implements MouseWheelListener, MouseListener, Runnable {
    @Setter
    private WindowFrame windowFrame;
    private final Entity entity;

    /**
     * 图像的大小始终不变
     * 在进行缩放时，通过计算点位落在图像上的位置进行处理放大和缩小
     */
    private final BufferedImage bufferImage;
    private final Rectangle rectangle;
    /**
     * 图像缩放比例
     */
    private double scale = (double) Configs.CALL_NUMBER / Configs.WINDOW_LENGTH;
    /**
     * 控制帧率所用毫秒
     */
    private long startTime = System.currentTimeMillis();
    /**
     * 数据是否需要重绘
     */
    private boolean redraw = false;

    public MyCanvas(Entity entity) {
        super();
        this.entity = entity;
        int windowLength = Configs.WINDOW_LENGTH;
        setPreferredSize(new Dimension(windowLength, windowLength));
        bufferImage = new BufferedImage(windowLength, windowLength, BufferedImage.TYPE_INT_ARGB);
        rectangle = new Rectangle(0, 0, Configs.CALL_NUMBER, Configs.CALL_NUMBER);
        this.move();
        this.draw();
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferImage, 0, 0, null);
    }

    /**
     * 添加移动鼠标的步骤
     */
    private void move() {
        final int[] xy = new int[2];
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                xy[0] = e.getX();
                xy[1] = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - xy[0];
                int dy = e.getY() - xy[1];
                rectangle.translate(dx, dy);
                draw();
                // log.debug(rectangle.toString());
                xy[0] = e.getX();
                xy[1] = e.getY();
            }
        });
    }

    /**
     * 绘画脑图
     */
    private void draw() {
        this.redraw = false;
        // 在缓冲区上执行具体的绘制操作
        int width = bufferImage.getWidth();
        int height = bufferImage.getHeight();
        int[] pixels = new int[width * height];
        bufferImage.setRGB(0, 0, width, height, pixels, 0, width);

        entity.getLinks().forEach((k, v) -> v.forEach(link -> {
            int x = rectangle.x + k;
            int y = rectangle.y + link.getCellId();
            x /= scale;
            y /= scale;
            /* 移动到边界外的就不显示了*/
            int windowLength = Configs.WINDOW_LENGTH;
            if (x < 0 || x > windowLength || y < 0 || y > windowLength) {
                return;
            }
            if (x + 1 > width || y + 1 > height) {
                return;
            }
            Double jm = link.getJm(); // todo 根据连接性调整颜色
            bufferImage.setRGB(x, y, Color.BLACK.getRGB());
        }));
        entity.setChange(true);
        this.redraw = true;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int wheelRotation = e.getWheelRotation();
        if (wheelRotation < 0) {
            /* 向上滚动 放大*/
            scale /= 1.1;
        } else if (wheelRotation > 0) {
            /* 向下滚动 缩小*/
            scale *= 1.1;
        }
        this.draw();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON2) {
            this.scale = 1;
            this.draw();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * 定时更新图像到桌布上
     */
    @Override
    public void run() {
        while (true) {
            if (!this.redraw) {
                ThreadPoolUtil.sleep(3);
                continue;
            }
            long now = System.currentTimeMillis();

            // 控制帧率
            int timer = windowFrame.isActive() ? 20 : 100;
            if (now - startTime < timer) {
                ThreadPoolUtil.sleep(1);
                continue;
            }
            startTime = now;
            this.repaint();
        }
    }

}
