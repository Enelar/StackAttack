package bsiapi.metrics;

/**
 * Класс, описывающий размеры объекта.
 */
public class Metrics {
    private final double w, h;

    /**
     * Конструктор класса.
     *
     * @param w ширина объекта.
     * @param h высота объекта.
     */
    public Metrics(final double w, final double h) {
        this.w = w;
        this.h = h;
    }

    /**
     * Функция получения ширины объекта.
     *
     * @return ширина объекта.
     */
    public double w() {
        return w;
    }

    /**
     * Функция получения высоты объекта.
     *
     * @return высота объекта.
     */
    public double h() {
        return h;
    }
}
