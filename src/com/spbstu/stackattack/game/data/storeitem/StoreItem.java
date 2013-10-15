package com.spbstu.stackattack.game.data.storeitem;

import java.util.Random;

public class StoreItem {
    public static enum Type {
        TYPE_1(0), TYPE_2(1), TYPE_3(2);

        private final int typeIndex;

        private Type(final int typeIndex) {
            this.typeIndex = typeIndex;
        }

        public static Type getType(final int typesAmount) {
            Random r = new Random();

            int typeIndex = r.nextInt(typesAmount);

            for (Type type : Type.values()) {
                if (type.typeIndex == typeIndex) {
                    return type;
                }
            }

            return TYPE_1;
        }
    }

    public final Type type;

    public double x, y;
    public double vx, vy;
    public double dx;

    public StoreItem(final Type type, final double x, final double y) {
        this.type = type;

        this.x = x;
        this.y = y;
    }
}
