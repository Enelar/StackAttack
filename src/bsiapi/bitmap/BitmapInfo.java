package bsiapi.bitmap;

import android.graphics.Bitmap;

/**
 * Класс, описывающий битмап.
 *
 * @author bsi
 */
public class BitmapInfo {
    private final Bitmap bitmap;
    private final int w, h;
    private final boolean rotated;

    /**
     * Конструктор класса.
     *
     * @param bitmap битмап.
     */
    public BitmapInfo(final Bitmap bitmap) {
        this(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
    }

    /**
     * Конструктор класса.
     *
     * @param bitmap битмап.
     * @param w виртуальная ширина битмапа.
     * @param h виртуальная высота битмапа.
     * @param rotated был ли битмап повернут.
     */
    public BitmapInfo(final Bitmap bitmap, final int w, final int h, final boolean rotated) {
        this.bitmap = bitmap;
        this.w = w;
        this.h = h;
        this.rotated = rotated;
    }

    /**
     * Функция получения битмапа.
     *
     * @return битмап.
     */
    public Bitmap bitmap() {
        return bitmap;
    }

    /**
     * Функция получения виртуальной ширины битмапа.
     *
     * @return виртуальная ширина битмапа.
     */
    public int w() {
        return w;
    }

    /**
     * Функция получения виртуальной высоты битмапа.
     *
     * @return виртуальная высота битмапа.
     */
    public int h() {
        return h;
    }

    /**
     * Функция получения информации о том, был ли битмап повернут.
     *
     * @return true, если была повернут, false иначе.
     */
    public boolean rotated() {
        return rotated;
    }
}
