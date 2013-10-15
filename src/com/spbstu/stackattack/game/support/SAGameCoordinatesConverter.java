package com.spbstu.stackattack.game.support;

import com.spbstu.stackattack.support.coords.SACoordinatesConverter;
import com.spbstu.stackattack.support.coords.SAVirtualCoordinate;

public class SAGameCoordinatesConverter {
    private static SACoordinatesConverter coordinatesConverter = SACoordinatesConverter.instance();

    private static int cellSize, offsetX;

    public static class FieldCoordinate extends SACoordinatesConverter.Coordinate<Double> {
        public FieldCoordinate(final double x, final double y) {
            super(x, y);
        }
    }

    public static void resize() {
        cellSize = Math.min(
                (int)(coordinatesConverter.vw() / (SAOptions.FIELD_WIDTH + 2 * SAOptions.FIELD_OFFSET)),
                (int)(coordinatesConverter.vh() / (SAOptions.FIELD_HEIGHT + 2 * SAOptions.FIELD_OFFSET))
        );

        offsetX = (int)(coordinatesConverter.vw() - cellSize * (SAOptions.FIELD_WIDTH + 2 * SAOptions.FIELD_OFFSET));
    }

    public static SAVirtualCoordinate fieldToVirtual(final FieldCoordinate fc) {
        return new SAVirtualCoordinate(
                (int)(offsetX + (fc.x + SAOptions.FIELD_OFFSET) * cellSize),
                (int)((fc.y + SAOptions.FIELD_OFFSET) * cellSize));
    }

    public static FieldCoordinate virtualToField(final SAVirtualCoordinate vc) {
        return new FieldCoordinate(
                (double)(vc.x - offsetX) / cellSize - SAOptions.FIELD_OFFSET,
                (double)vc.y / cellSize - SAOptions.FIELD_OFFSET);
    }
}
