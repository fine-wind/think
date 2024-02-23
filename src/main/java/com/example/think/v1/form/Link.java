package com.example.think.v1.form;

import com.example.think.utils.RandomUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 连接
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Link implements Serializable {
    private Integer cellId;
    /**
     * todo 连接紧密度
     */
    private Double jm = 0d;

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
