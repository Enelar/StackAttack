package bsiapi.bitmap.conversion.base;

import bsiapi.bitmap.BitmapInfo;

/**
 * Класс преобразования информации о битмапе.
 *
 * @author bsi
 */
public abstract class BitmapInfoTransformator {
    /**
     * Функция преобразования информации о битмапе.
     *
     * @param bitmapInfo битмап для преобразования.
     *
     * @return преобразованный битмап.
     */
    public abstract BitmapInfo convert(final BitmapInfo bitmapInfo);
}
