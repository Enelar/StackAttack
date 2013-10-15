package com.spbstu.stackattack.game.drawer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.spbstu.stackattack.game.data.StackAttackData;
import com.spbstu.stackattack.game.support.SAGameCoordinatesConverter;
import com.spbstu.stackattack.support.coords.SACoordinatesConverter;
import com.spbstu.stackattack.support.coords.SAScreenCoordinate;
import com.spbstu.stackattack.support.coords.SAVirtualCoordinate;

/**
 * Stack attack drawer representation class.
 *
 * @author bsi
 */
public abstract class StackAttackDrawer {
    private final SACoordinatesConverter coordinatesConverter = SACoordinatesConverter.instance();

    protected final StackAttackData saData;

    protected StackAttackDrawer(final StackAttackData saData) {
        this.saData = saData;
    }

    public abstract void draw(final Canvas c);

    public void drawRect(final Canvas c, final double x, final double y, final double w, final double h, final Paint p) {
        SAVirtualCoordinate leftTopVC =
                SAGameCoordinatesConverter.fieldToVirtual(new SAGameCoordinatesConverter.FieldCoordinate(x + w, y + h));
        SAVirtualCoordinate rightBottomVC =
                SAGameCoordinatesConverter.fieldToVirtual(new SAGameCoordinatesConverter.FieldCoordinate(x, y));

        SAScreenCoordinate leftTopSC = coordinatesConverter.virtualToScreen(leftTopVC);
        SAScreenCoordinate rightBottomSC = coordinatesConverter.virtualToScreen(rightBottomVC);

        c.drawRect(leftTopSC.x, leftTopSC.y, rightBottomSC.x, rightBottomSC.y, p);
    }

    public void drawBitmap(final Canvas c, final Bitmap bm, final double x, final double y, final double w, final double h) {
        SAVirtualCoordinate leftTopVC =
                SAGameCoordinatesConverter.fieldToVirtual(new SAGameCoordinatesConverter.FieldCoordinate(x + w, y + h));
        SAScreenCoordinate leftTopSC = coordinatesConverter.virtualToScreen(leftTopVC);

        c.drawBitmap(bm, leftTopSC.x, leftTopSC.y, null);
    }
}
