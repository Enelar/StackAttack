package bsiapi.ui.element;

import bsiapi.drawer.Drawer;

/**
 * Элемент графического пользовательского интерфейса.
 *
 * @author bsi
 */
public abstract class UIElement {
    private final UIElementTouchListener touchListener;

    protected boolean pressed = false;

    /**
     * Конструктор класса.
     */
    protected UIElement() {
        this(null);
    }

    /**
     * Конструктор класса.
     *
     * @param touchListener объект реакции на нажатие.
     */
    protected UIElement(UIElementTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    /**
     * Функция отрисовки элемента.
     *
     * @param drawer объект для отрисовки графических примитивов.
     */
    public abstract void draw(final Drawer drawer);

    /**
     * Функция реакции на нажатие.
     *
     * @param touchInfo информация о нажатии.
     */
    public void touch(final UITouchInfo touchInfo) {
        if (touchInfo.getTouchType().equals(UITouchInfo.Type.UP)) {
            pressed = false;
        }

        if (touched(touchInfo)) {
            if (touchInfo.getTouchType().equals(UITouchInfo.Type.DOWN)) {
                pressed = true;
            }

            if (touchListener != null) {
                touchListener.onTouch(touchInfo);
            }
        }
    }

    /**
     * Функция, проверяющая было ли нажатие на текущий элемент.
     *
     * @param touchInfo информация о нажатии.
     *
     * @return тип нажатия.
     */
    protected abstract boolean touched(final UITouchInfo touchInfo);
}
