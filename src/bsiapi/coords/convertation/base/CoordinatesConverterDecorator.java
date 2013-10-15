package bsiapi.coords.convertation.base;

import bsiapi.coords.Coordinate;

/**
 * Декоратор для класса преобразования экранных координат.
 *
 * @author bsi
 */
public abstract class CoordinatesConverterDecorator extends CoordinatesConverter {
    private final CoordinatesConverter coordinatesConverter;

    /**
     * Конструктор класса.
     *
     * @param sw ширина реального экрана.
     * @param sh высота реального экрана.
     */
    public CoordinatesConverterDecorator(final double sw, final double sh) {
        super(sw, sh);

        this.coordinatesConverter = null;
    }

    /**
     * Конструктор класса.
     *
     * @param coordinatesConverter внутренний преобразователь координат. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     */
    public CoordinatesConverterDecorator(final CoordinatesConverter coordinatesConverter) {
        super(coordinatesConverter.vw(), coordinatesConverter.vh());

        this.coordinatesConverter = coordinatesConverter;
    }

    @Override
    public final Coordinate screenToVirtual(final Coordinate sc) {
        if (coordinatesConverter == null) {
            return screenToVirtualInner(sc);
        }

        return screenToVirtualInner(coordinatesConverter.screenToVirtual(sc));
    }

    @Override
    public final Coordinate virtualToScreen(final Coordinate vc) {
        if (coordinatesConverter == null) {
            return virtualToScreenInner(vc);
        }

        return virtualToScreenInner(coordinatesConverter.virtualToScreen(vc));
    }

    /**
     * Функция преобразования реальных экранных координат в виртуальные экранные координаты.
     *
     * @param sc реальные экранные координаты.
     *
     * @return виртуальные экранные координаты.
     */
    protected abstract Coordinate screenToVirtualInner(final Coordinate sc);

    /**
     * Функция преобразования виртуальных экранных координат в реальные экранные координаты.
     *
     * @param vc виртуальные экранные координаты.
     *
     * @return реальные экранные координаты.
     */
    protected abstract Coordinate virtualToScreenInner(final Coordinate vc);
}
