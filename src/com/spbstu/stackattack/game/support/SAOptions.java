package com.spbstu.stackattack.game.support;

/**
 * Stack attack options representation class.
 *
 * @author bsi
 */
public class SAOptions {
    public static final int FIELD_WIDTH = 12;
    public static final int FIELD_HEIGHT = 7;

    public static final double FIELD_OFFSET = 0.1;

    public static final int PLAYER_HEIGHT = 2;

    public static final double FALLING_STORE_ITEM_SPEED = 2.5;
    public static final double STORE_ITEM_FREE_FALL_ACCELERATION = 0.0;

    public static final double PLAYER_FREE_FALL_ACCELERATION = 8.0;
    public static final double PLAYER_JUMP_HEIGHT = 2.0;
    public static final double PLAYER_JUMP_SPEED = Math.sqrt(2 * PLAYER_JUMP_HEIGHT * PLAYER_FREE_FALL_ACCELERATION);

    public static final double PLAYER_MOVE_SPEED = 3.0;

    public static final double CRANE_MOVE_SPEED = 3.0;
    public static final double CRANE_OBJECT_GENERATION_FREQUENCY = 3.0;
}
