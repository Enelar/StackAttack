package bsiapi.ui.button;

import bsiapi.bitmap.BitmapInfo;
import bsiapi.coords.Coordinate;
import bsiapi.drawer.Drawer;
import bsiapi.ui.element.UIElement;
import bsiapi.ui.element.UIElementTouchListener;
import bsiapi.ui.element.UITouchInfo;

/**
 * Класс, описывающий кнопку.
 *
 * @author bsi
 */
public class UIButton extends UIElement {
    private final BitmapInfo bitmapFree, bitmapPressed;
    protected final Coordinate vc;

    /**
     * Конструктор класса.
     *
     * @param touchListener объект реакции на нажатие.
     * @param bitmapFree битмап, отрисовывающийся, когда кнопка не нажата.
     * @param bitmapPressed  битмап, отрисовывающийся, когда кнопка нажата.
     * @param vc виртуальные координаты кнопки.
     */
    public UIButton(final UIElementTouchListener touchListener, final BitmapInfo bitmapFree, final BitmapInfo bitmapPressed, final Coordinate vc) {
        super(touchListener);

        this.bitmapFree = bitmapFree;
        this.bitmapPressed = bitmapPressed;
        this.vc = vc;
    }

    @Override
    public void draw(final Drawer drawer) {
        if (!pressed) {
            drawer.drawBitmap(bitmapFree, vc);
        } else {
            drawer.drawBitmap(bitmapPressed, vc);
        }
    }

    @Override
    protected boolean touched(UITouchInfo touchInfo) {
        return vc.x() <= touchInfo.getTouchVC().x() && touchInfo.getTouchVC().x() <= vc.x() + bitmapVW() &&
            vc.y() <= touchInfo.getTouchVC().y() && touchInfo.getTouchVC().y() <= vc.y() + bitmapVH();
    }

    /**
     * Функция получения ширины отрисовываемого битмапа.
     *
     * @return ширина отрисовываемого битмапа.
     */
    protected final double bitmapVW() {
        return bitmapFree.w();
    }

    /**
     * Функция получения высоты отрисовываемого битмапа.
     *
     * @return высота отрисовываемого битмапа.
     */
    protected final double bitmapVH() {
        return bitmapFree.h();
    }
}
