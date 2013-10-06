package com.spbstu.stackattack.game.data;

import com.spbstu.stackattack.game.support.StackAttackOptions;

public class Player {
    public double x, y;
    public double vx = 0, vy = 0;
    public double dx = 0;

    public Player(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        vx = -StackAttackOptions.PLAYER_MOVE_SPEED;
        dx = -1;
    }

    public void moveRight() {
        vx = StackAttackOptions.PLAYER_MOVE_SPEED;
        dx = 1;
    }

    public void moveBack() {
        if (vx < 0) {
            dx = 1 + dx;
        } else {
            dx = 1 - dx;
        }

        vx = -vx;
    }

    public void jump() {
        vy = StackAttackOptions.PLAYER_JUMP_SPEED;
    }
}
