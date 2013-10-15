package bsiapi.text.conversion.base;

import bsiapi.text.Text;

/**
 * Класс преобразования информации о тексте.
 *
 * @author bsi
 */
public abstract class TextTransformator {
    /**
     * Функция преобразования информации о тексте.
     *
     * @param text текст для преобразования.
     *
     * @return преобразованный текст.
     */
    public abstract Text transform(final Text text);
}
