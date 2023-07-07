package com.example.think.world;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 小小的脑袋瓜子
 * todo 发育 组织 学习
 */
@Slf4j
public class Brain implements Runnable {
    /**
     * 最大脑层
     */
    final int MAX_LAYERS = 100;
    long countCall = 0;
    /**
     * 层
     */
    LinkedList<Layer> layers = new LinkedList<>();

    /**
     * Runs this operation.
     */
    @SneakyThrows
    @Override
    public void run() {
        this.addLayer(1);
        this.ponder();
        while (true) {
            log.debug("思索 总线程{} 总细胞{}", Thread.activeCount(), countCall);
            Thread.sleep(10000);
        }
    }

    /**
     * 思考
     * 脑子是个好东西, 用它想点什么呢？
     */
    @SneakyThrows
    private void ponder() {
        /* 最外一层的细胞*/
        layers.getLast()
                .getCells()
                .forEach(cell -> {
                    // 随便想了点什么，发现不行 自动增长一层
                    // todo 这怎么生的三
                    cell.input(null);
                });
        if (layers.size() < MAX_LAYERS) {
            this.addLayer(layers.size() + 1);
            this.ponder();
        }
    }

    private void addLayer(int i) {

        Layer e = new Layer(i);
        layers.add(e);
        countCall += e.getCells().size();
    }
}
