package com.example.think.dom;

import com.example.think.enums.MoveEnum;
import lombok.Getter;

@Getter
public class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point move(MoveEnum move) {
        switch (move) {
            case u -> y++;
            case d -> y--;
            case l -> x--;
            case r -> x++;
        }
        return this;
    }
}
