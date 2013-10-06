package com.spbstu.stackattack.game.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.spbstu.stackattack.game.data.StackAttackData;
import com.spbstu.stackattack.game.support.StackAttackCoordinatesConverter;

/**
 * Stack attack drawer representation class.
 *
 * @author bsi
 */
public abstract class StackAttackDrawer {
    protected final StackAttackData saData;

    protected final StackAttackCoordinatesConverter converter = StackAttackCoordinatesConverter.getInstance();

    /**
     * Class constructor.
     *
     * @param saData reference to a game data.
     */
    protected StackAttackDrawer(final StackAttackData saData) {
        this.saData = saData;
    }

    /**
     * Draw stack attack game data function.
     *
     * @param c canvas to draw into.
     */
    public abstract void draw(final Canvas c);

    /**
     * Draw rectangle function.
     *
     * @param c canvas to draw into.
     * @param x x rectangle coordinate.
     * @param y y rectangle coordinate.
     * @param w rectangle widht.
     * @param h rectangle height.
     * @param p paint to draw.
     */
    public void drawRect(final Canvas c, final double x, final double y, final double w, final double h, final Paint p) {
        StackAttackCoordinatesConverter.VirtualCoordinate leftTopVC =
                converter.fieldToVirtual(new StackAttackCoordinatesConverter.FieldCoordinate(x + w, y + h));
        StackAttackCoordinatesConverter.VirtualCoordinate rightBottomVC =
                converter.fieldToVirtual(new StackAttackCoordinatesConverter.FieldCoordinate(x, y));

        StackAttackCoordinatesConverter.ScreenCoordinate leftTopSC =
                converter.virtualToScreen(leftTopVC);
        StackAttackCoordinatesConverter.ScreenCoordinate rightBottomSC =
                converter.virtualToScreen(rightBottomVC);

        c.drawRect(leftTopSC.x, leftTopSC.y, rightBottomSC.x, rightBottomSC.y, p);
    }
}
