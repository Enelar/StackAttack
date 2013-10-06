package com.spbstu.stackattack.game.support;

/**
 * Stack attack coordinates converter class.
 *
 * @author bsi
 */
public class StackAttackCoordinatesConverter {
    /**
     * Screen coordinate representation class.
     *
     * @author bsi
     */
    public static class ScreenCoordinate {
        public int x, y;

        /**
         * Class constructor.
         *
         * @param x x-axis coordinate value.
         * @param y y-axis coordinate value.
         */
        public ScreenCoordinate(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Virtual coordinate representation class.
     *
     * @author bsi
     */
    public static class VirtualCoordinate {
        public int x, y;

        /**
         * Class constructor.
         *
         * @param x x-axis coordinate value.
         * @param y y-axis coordinate value.
         */
        public VirtualCoordinate(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Stack attack game field coordinate representation class.
     *
     * @author bsi
     */
    public static class FieldCoordinate {
        public double x, y;

        /**
         * Class constructor.
         *
         * @param x x-axis coordinate value.
         * @param y y-axis coordinate value.
         */
        public FieldCoordinate(final double x, final double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static StackAttackCoordinatesConverter converter = new StackAttackCoordinatesConverter();

    private int w, h, vw, vh, vCellSize, vOffsetX;

    public static StackAttackCoordinatesConverter getInstance() {
        return converter;
    }

    /**
     * Response on resize function.
     *
     * @param w new screen width.
     * @param h new screen height.
     */
    public void resize(final int w, final int h) {
        this.w = w;
        this.h = h;

        vw = Math.max(w, h);
        vh = Math.min(w, h);

        vCellSize = Math.min(
                (int)(vw / (StackAttackOptions.FIELD_WIDTH + 2 * StackAttackOptions.FIELD_OFFSET)),
                (int)(vh / (StackAttackOptions.FIELD_HEIGHT + 2 * StackAttackOptions.FIELD_OFFSET))
        );

        vOffsetX = (int)(vw - vCellSize * (StackAttackOptions.FIELD_WIDTH + 2 * StackAttackOptions.FIELD_OFFSET));
    }

    /**
     * Convert virtual coordinates to screen function.
     *
     * @param vc virtual coordinates.
     *
     * @return screen coordinates.
     */
    public ScreenCoordinate virtualToScreen(final VirtualCoordinate vc) {
        if (w > h) {
            return new ScreenCoordinate(0, 0); /* ToDo */
        } else {
            return new ScreenCoordinate(w - vc.y, h - vc.x);
        }
    }

    /**
     * Convert screen coordinates to virtual function.
     *
     * @param sc screen coordinates.
     *
     * @return virtual coordinates.
     */
    public VirtualCoordinate screenToVirtual(final ScreenCoordinate sc) {
        if (w > h) {
            return new VirtualCoordinate(0, 0); /* ToDo */
        } else {
            return new VirtualCoordinate(h - sc.y, w - sc.x);
        }
    }

    /**
     * Convert field coordinates to virtual function.
     *
     * @param fc field coordinates.
     *
     * @return virtual coordinates.
     */
    public VirtualCoordinate fieldToVirtual(final FieldCoordinate fc) {
        return new VirtualCoordinate(
                (int)(vOffsetX + (fc.x + StackAttackOptions.FIELD_OFFSET) * vCellSize),
                (int)((fc.y + StackAttackOptions.FIELD_OFFSET) * vCellSize));
    }

    /**
     * Convert virtual coordinates to field function.
     *
     * @param vc virtual coordinates.
     *
     * @return field coordinates.
     */
    public FieldCoordinate virtualToField(final VirtualCoordinate vc) {
        return new FieldCoordinate(
                (double)(vc.x - vOffsetX) / vCellSize - StackAttackOptions.FIELD_OFFSET,
                (double)vc.y / vCellSize - StackAttackOptions.FIELD_OFFSET);
    }

    private StackAttackCoordinatesConverter() {}
}
