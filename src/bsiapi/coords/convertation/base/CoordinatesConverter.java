package bsiapi.coords.convertation.base;

import bsiapi.coords.Coordinate;

/**
 * Класс преобразования экранных координат.
 * 
 * @author bsi
 */
public abstract class CoordinatesConverter {
    protected final double sw, sh;

    /**
     * Конструктор класса.
     *
     * @param sw ширина реального экрана.
     * @param sh высота реального экрана.
     */
    public CoordinatesConverter(final double sw, final double sh) {
        this.sw = sw;
        this.sh = sh;
    }

    /**
     * Функция преобразования реальных экранных координат в виртуальные экранные координаты.
     *
     * @param sc реальные экранные координаты.
     *
     * @return виртуальные экранные координаты.
     */
    public abstract Coordinate screenToVirtual(final Coordinate sc);

    /**
     * Функция преобразования виртуальных экранных координат в реальные экранные координаты.
     *
     * @param vc виртуальные экранные координаты.
     *
     * @return реальные экранные координаты.
     */
    public abstract Coordinate virtualToScreen(final Coordinate vc);

    /**
     * Функция получения ширины виртуального экрана.
     *
     * @return ширина виртуального экрана.
     */
    public abstract double vw();

    /**
     * Функция получения высоты виртуального экрана.
     *
     * @return высота виртуального экрана.
     */
    public abstract double vh();
}
