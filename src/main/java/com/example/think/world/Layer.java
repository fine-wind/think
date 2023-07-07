package com.example.think.world;

import com.example.think.world.form.SimpleCell;
import com.example.think.world.form.Link;

import java.util.*;

/**
 * 层层叠叠
 */
public class Layer {
    /**
     * 本层所有细胞
     */
    private final LinkedList<SimpleCell> cellMap = new LinkedList<>();

    public Layer(int layer) {
        int initialCapacity = layer << 1;
        /* 创建细胞*/
        for (int i = 0; i < initialCapacity; i++) {
            SimpleCell cell = new SimpleCell((layer - 1) + "_" + i);
            cellMap.add(cell);
        }
        /* 建立联系 这里上下级没有连通*/
        this.cellMap.forEach(cell -> cell.link(cellMap));
    }

    public Collection<SimpleCell> getCells() {
        return cellMap;
    }
}
