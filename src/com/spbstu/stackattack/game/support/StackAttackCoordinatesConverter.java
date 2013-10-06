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

        vCellSize = determineCellSize(vw, vh);

        vOffsetX = (int)(vw - makeUncommentedMagic(vCellSize, StackAttackOptions.FIELD_WIDTH));
    }
    
    private int determineCellSize(final int vw, final int vh) {
        int
          magicBasedOnWidth = makeUncommentedMagic(vw, StackAttackOptions.FIELD_WIDTH),
          magicBasedOnHeight = makeUncommentedMagic(vh, StackAttackOptions.FIELD_HEIGHT);
        return Math.min(magicBasedOnWidth, magicBasedOnHeight);
    }
    
    private int makeUncommentedMagic(final int base, final int magicSource) {
      return base / (magicSource + 2 * StackAttackOptions.FIELD_OFFSET);
    }

    /**
     * Convert virtual coordinates to screen function.
     *
     * @param vc virtual coordinates.
     *
     * @return screen coordinates.
     */
    public ScreenCoordinate virtualToScreen(final VirtualCoordinate vc) {
        return screen2VirtualConvertSuit<ScreenCoordinate, VirtualCoordinate>(vc);
    }

    /**
     * Convert screen coordinates to virtual function.
     *
     * @param sc screen coordinates.
     *
     * @return virtual coordinates.
     */
    public VirtualCoordinate screenToVirtual(final ScreenCoordinate sc) {
      return screen2VirtualConvertSuit<VirtualCoordinate, ScreenCoordinate>(sc);
    }
    
    private<A, B> A screen2VirtualConvertSuit(final B origin) {
        if (w > h) {
            return new A(0, 0); /* ToDo */
        } else {
            return new A(h - origin.y, w - origin.x);
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
