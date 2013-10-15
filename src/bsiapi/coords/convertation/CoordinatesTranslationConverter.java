package bsiapi.coords.convertation;

import bsiapi.coords.Coordinate;
import bsiapi.coords.convertation.base.CoordinatesConverter;
import bsiapi.coords.convertation.base.CoordinatesConverterDecorator;

/**
 * Класс преобразования экранных координат.
 * Сдвигает экранные координаты на установленную величину.
 */
public final class CoordinatesTranslationConverter extends CoordinatesConverterDecorator {
    private final double tx, ty;

    /**
     * Конструктор класса.
     *
     * @param coordinatesConverter внутренний преобразователь координат. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     *
     * @param tx величина сдвига по оси x.
     * @param tx величина сдвига по оси y.
     */
    public CoordinatesTranslationConverter(final CoordinatesConverter coordinatesConverter, final double tx, final double ty) {
        super(coordinatesConverter);

        this.tx = tx;
        this.ty = ty;
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