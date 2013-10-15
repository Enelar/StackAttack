package bsiapi.coords.convertation;

import bsiapi.coords.Coordinate;
import bsiapi.coords.convertation.base.CoordinatesConverter;
import bsiapi.coords.convertation.base.CoordinatesConverterDecorator;

/**
 * Класс преобразования экранных координат.
 * Сжимает координаты в установленные рамки и делает отступ от краев так, что получившаяся сетка центрируется.
 */
public final class CoordinatesScaleConverter extends CoordinatesConverterDecorator {
    private final double w, h;

    /**
     * Конструктор класса.
     *
     * @param coordinatesConverter внутренний преобразователь координат. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     *
     * @param w требуемая ширина вирутального экрана.
     * @param h требуемая высота вирутального экрана.
     */
    public CoordinatesScaleConverter(final CoordinatesConverter coordinatesConverter, final double w, final double h) {
        super(coordinatesConverter);

        this.w = w;
        this.h = h;
    }

    @Override
    protected Coordinate screenToVirtualInner(final Coordinate sc) {
        return sc; /* ToDo */
    }

    @Override
    protected Coordinate virtualToScreenInner(final Coordinate vc) {
        return vc; /* ToDo */
    }

    @Override
    public double vw() {
        return sw;
    }

    @Override
    public double vh() {
        return sh;
    }
}
