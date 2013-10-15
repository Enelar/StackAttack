package bsiapi.metrics;

import bsiapi.coords.Coordinate;
import bsiapi.coords.convertation.base.CoordinatesConverter;

/**
 * Класс преобразования размеров.
 *
 * @author bsi
 */
public final class MetricsConverter {
    private final CoordinatesConverter coordinatesConverter;

    /**
     * Конструктор класса.
     *
     * @param coordinatesConverter преобразователь координат для пересчета размеров.
     */
    public MetricsConverter(final CoordinatesConverter coordinatesConverter) {
        this.coordinatesConverter = coordinatesConverter;
    }

    /**
     * Функция преобразования реальных размеров объекта в виртуальные размеры.
     *
     * @param rm реальные размеры объекта.
     *
     * @return виртуальные размеры объекта.
     */
    public Metrics convertToVirtual(final Metrics rm) {
        Coordinate p1 = coordinatesConverter.screenToVirtual(new Coordinate(0, 0));
        Coordinate p2 = coordinatesConverter.screenToVirtual(new Coordinate(rm.w(), rm.h()));

        return new Metrics(Math.abs(p2.x() - p1.x()), Math.abs(p2.y() - p1.y()));
    }

    /**
     * Функция преобразования виртуальных размеров объекта в реальные размеры.
     *
     * @param vm виртуальные размеры объекта.
     *
     * @return реальные размеры объекта.
     */
    public Metrics convertToReal(final Metrics vm) {
        Coordinate p1 = coordinatesConverter.virtualToScreen(new Coordinate(0, 0));
        Coordinate p2 = coordinatesConverter.virtualToScreen(new Coordinate(vm.w(), vm.h()));

        return new Metrics(Math.abs(p2.x() - p1.x()), Math.abs(p2.y() - p1.y()));
    }
}
