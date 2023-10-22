package com.example.think.v1.form;

import com.example.think.utils.RandomUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 连接
 */
@Data
@Accessors(chain = true)
public class Link {
    private Integer cellId;
    /**
     * todo 连接紧密度
     */
    private Double jm = 0d;

    public Link() {
    }

    public Link(int cellId) {
        this.cellId = cellId;
    }

    public static Link random(int cellSize) {
        int i = RandomUtil.nextCellId(cellSize);
        return new Link(i);
    }

    /**
     * todo 连接紧密度如何调控细胞之间的重要性？
     */
    public void run() {

    }
}
