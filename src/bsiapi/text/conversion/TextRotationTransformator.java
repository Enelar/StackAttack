package bsiapi.text.conversion;

import bsiapi.metrics.Metrics;
import bsiapi.text.Text;
import bsiapi.text.conversion.base.TextTransformator;
import bsiapi.text.conversion.base.TextTransformatorDecorator;

public class TextRotationTransformator extends TextTransformatorDecorator {
    private final Metrics screenMetrics;

    /**
     * Конструктор класса.
     *
     * @param screenMetrics размеры экрана.
     */
    public TextRotationTransformator(final Metrics screenMetrics) {
        this.screenMetrics = screenMetrics;
    }

    /**
     * Конструктор класса.
     *
     * @param textTransformator внутренний преобразователь битмапа. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     * @param screenMetrics размеры экрана.
     */
    public TextRotationTransformator(final TextTransformator textTransformator, final Metrics screenMetrics) {
        super(textTransformator);

        this.screenMetrics = screenMetrics;
    }

    @Override
    public Text transformInner(final Text text) {
        if (screenMetrics.w() >= screenMetrics.h()) {
            return text;
        }

        return new Text(text.getText(), text.getRotationAngle() - 90, text.getSizeMultiplier() * screenMetrics.h() / screenMetrics.w());
    }
}
