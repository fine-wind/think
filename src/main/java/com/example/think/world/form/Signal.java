package com.example.think.world.form;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 简简单单的细胞信号
 */
@Data
@Accessors(chain = true)
public class Signal {
    private String cellId;
    /**
     * 信号
     */
    private double signal;

    public Signal() {
    }

    public Signal(double signal) {
        this.signal = signal;
    }

}
