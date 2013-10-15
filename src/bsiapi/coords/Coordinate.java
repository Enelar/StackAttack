package bsiapi.coords;

/**
 * Экранная координата.
 *
 * @author bsi
 */
public class Coordinate {
    private final double x, y;

    /**
     * Конструктор класса.
     *
     * @param x экранная координата по оси x.
     * @param y экранная координата по оси y.
     */
    public Coordinate(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Функция получения экранной координаты по оси x.
     *
     * @return экранная координата по оси x.
     */
    public double x() {
        return x;
    }

    /**
     * Функция получения экранной координаты по оси y.
     *
     * @return экранная координата по оси y.
     */
    public double y() {
        return y;
    }
}