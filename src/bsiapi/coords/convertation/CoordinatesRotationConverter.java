package bsiapi.coords.convertation;

import bsiapi.coords.Coordinate;
import bsiapi.coords.convertation.base.CoordinatesConverter;
import bsiapi.coords.convertation.base.CoordinatesConverterDecorator;

/**
 * Класс преобразования экранных координат.
 * Если ширина экрана больше высоты экрана, то преобразование не делает ничего. Иначе, экран поворачивается на 90 градусов против часовой стрелки.
 *
 * @author bsi
 */
public final class CoordinatesRotationConverter extends CoordinatesConverterDecorator {
    /**
     * Конструктор класса.
     *
     * @param sw ширина реального экрана.
     * @param sh высота реального экрана.
     */
    public CoordinatesRotationConverter(final double sw, final double sh) {
        super(sw, sh);
    }

    /**
     * Конструктор класса.
     *
     * @param coordinatesConverter внутренний преобразователь координат. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     */
    public CoordinatesRotationConverter(final CoordinatesConverter coordinatesConverter) {
        super(coordinatesConverter);
    }

    @Override
    protected Coordinate screenToVirtualInner(final Coordinate sc) {
        if (sw >= sh) {
            return new Coordinate(sc.x(), sc.y());
        } else {
            return new Coordinate(sh - sc.y(), sw - sc.x());
        }
    }

    @Override
    protected Coordinate virtualToScreenInner(final Coordinate vc) {
        if (sw >= sh) {
            return new Coordinate(vc.x(), vc.y());
        } else {
            return new Coordinate(sw - vc.y(), sh - vc.x());
        }
    }

    @Override
    public double vw() {
        return Math.max(sw, sh);
    }

    @Override
    public double vh() {
        return Math.min(sw, sh);
    }
}
