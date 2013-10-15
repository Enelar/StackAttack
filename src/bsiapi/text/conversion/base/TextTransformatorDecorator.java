package bsiapi.text.conversion.base;

import bsiapi.text.Text;

/**
 * Декоратор класса преобразования информации о тексте.
 *
 * @author bsi
 */
public abstract class TextTransformatorDecorator extends TextTransformator {
    private final TextTransformator textTransformator;

    /**
     * Конструктор класса.
     */
    public TextTransformatorDecorator() {
        this.textTransformator = null;
    }

    /**
     * Конструктор класса.
     *
     * @param textTransformator внутренний преобразователь битмапа. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     */
    public TextTransformatorDecorator(final TextTransformator textTransformator) {
        this.textTransformator = textTransformator;
    }

    @Override
    public final Text transform(final Text text) {
        if (textTransformator == null) {
            return transformInner(text);
        }

        return transformInner(textTransformator.transform(text));
    }

    /**
     * Функция преобразования информации о тексте.
     *
     * @param text текст для преобразования.
     *
     * @return преобразованный текст.
     */
    public abstract Text transformInner(final Text text);
}
