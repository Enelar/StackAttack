package bsiapi.bitmap.conversion.base;

import bsiapi.bitmap.BitmapInfo;

/**
 * Декоратор класса преобразования информации о битмапе.
 *
 * @author bsi
 */
public abstract class BitmapInfoTransformatorDecorator extends BitmapInfoTransformator {
    private final BitmapInfoTransformator bitmapInfoTransformator;

    /**
     * Конструктор класса.
     */
    protected BitmapInfoTransformatorDecorator() {
        this.bitmapInfoTransformator = null;
    }

    /**
     * Конструктор класса.
     *
     * @param bitmapInfoTransformator внутренний преобразователь битмапа. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     */
    protected BitmapInfoTransformatorDecorator(final BitmapInfoTransformator bitmapInfoTransformator) {
        this.bitmapInfoTransformator = bitmapInfoTransformator;
    }

    @Override
    public final BitmapInfo convert(final BitmapInfo bitmapInfo) {
        if (bitmapInfoTransformator == null) {
            return convertInner(bitmapInfo);
        }

        return convertInner(bitmapInfoTransformator.convert(bitmapInfo));
    }

    /**
     * Функция преобразования информации о битмапе.
     *
     * @param bitmapInfo битмап для преобразования.
     *
     * @return преобразованный битмап.
     */
    protected abstract BitmapInfo convertInner(final BitmapInfo bitmapInfo);
}
