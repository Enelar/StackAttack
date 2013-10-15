package bsiapi.ui.element;

import android.view.MotionEvent;
import bsiapi.coords.Coordinate;
import bsiapi.coords.convertation.base.CoordinatesConverter;

/**
 * Класс, описывающий информацию о нажатии на экран.
 *
 * @author bsi
 */
public class UITouchInfo {
    /**
     * Тип нажатия.
     *
     * @author bsi
     */
    public static enum Type {
        UP, DOWN, STATIC;
    }

    private final Coordinate touchVC;
    private final Type touchType;

    /**
     * Конструктор класса.
     *
     * @param e сырая информация о нажатии.
     * @param coordinatesConverter преобразователь координат.
     */
    public UITouchInfo(final MotionEvent e, final CoordinatesConverter coordinatesConverter) {
        touchVC = coordinatesConverter.screenToVirtual(new Coordinate(e.getX(), e.getY()));

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                touchType = Type.UP;
                break;
            case MotionEvent.ACTION_DOWN:
                touchType = Type.DOWN;
                break;
            default:
                touchType = Type.STATIC;
                break;
        }
    }

    /**
     * Функция получения виртуальных координат нажатия.
     *
     * @return виртуальные координаты нажатия.
     */
    public Coordinate getTouchVC() {
        return touchVC;
    }

    /**
     * Функция получения типа нажатия.
     *
     * @return тип нажатия.
     */
    public Type getTouchType() {
        return touchType;
    }
}
