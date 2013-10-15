package bsiapi.ui.element;

/**
 * Класс реакции на нажатие на UI элемент.
 *
 * @author bsi
 */
public abstract class UIElementTouchListener {
    /**
     * Функция реакции на нажатие на UI элемент.
     *
     * @param touchInfo информация о нажатии.
     */
    public abstract void onTouch(final UITouchInfo touchInfo);
}
