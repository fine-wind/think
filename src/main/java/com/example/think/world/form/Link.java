package com.example.think.world.form;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * 简简单单的细胞间流量参数
 */
@Accessors(chain = true)
public class Link {
    @Getter
    private SimpleCell upCell;
    @Setter
    @Getter
    private double weight = 0.5;
    private final LinkedList<SimpleCell> nextCells = new LinkedList<>();

    public void next(Signal signal) {
        if (Objects.isNull(signal)) {
            return;
        }
        nextCells.forEach(nextCell -> {
            if (Objects.equals(signal.getCellId(), nextCell.getId())) {
                nextCells.remove(nextCell);
                return;
            }
            if (Objects.equals(nextCell, upCell)) {
                weight *= 0.5;
            }
            nextCell.input(new Signal(signal.getSignal() * weight).setCellId(signal.getCellId()));
        });
    }

    public Link link(SimpleCell upCell, Collection<SimpleCell> cells) {
        this.upCell = upCell;
        this.nextCells.addAll(cells);
        return this;
    }
}
