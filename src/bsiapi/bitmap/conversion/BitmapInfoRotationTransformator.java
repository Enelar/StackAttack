package bsiapi.bitmap.conversion;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import bsiapi.bitmap.BitmapInfo;
import bsiapi.bitmap.conversion.base.BitmapInfoTransformator;
import bsiapi.bitmap.conversion.base.BitmapInfoTransformatorDecorator;
import bsiapi.metrics.Metrics;

/**
 * Класс преобразования информации о битмапе.
 * Если ширина экрана больше высоты, то трансформация не производится. Иначе, битмап поворачивается против часовой стрелки на 90 градусов.
 *
 * @author bsi
 */
public final class BitmapInfoRotationTransformator extends BitmapInfoTransformatorDecorator {
    private final Metrics screenMetrics;

    /**
     * Конструктор класса.
     *
     * @param screenMetrics размеры экрана.
     */
    public BitmapInfoRotationTransformator(final Metrics screenMetrics) {
        this.screenMetrics = screenMetrics;
    }

    /**
     * Конструктор класса.
     *
     * @param bitmapInfoTransformator внутренний преобразователь битмапа. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     * @param screenMetrics размеры экрана.
     */
    public BitmapInfoRotationTransformator(final BitmapInfoTransformator bitmapInfoTransformator, final Metrics screenMetrics) {
        super(bitmapInfoTransformator);

        this.screenMetrics = screenMetrics;
    }

    @Override
    protected BitmapInfo convertInner(final BitmapInfo bitmapInfo) {
        if (screenMetrics.w() >= screenMetrics.h()) {
            return bitmapInfo;
        } else {
            Matrix matrix = new Matrix();

            matrix.postRotate(-90);

            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmapInfo.bitmap(), 0, 0, bitmapInfo.w(), bitmapInfo.h(), matrix, true);

            return new BitmapInfo(rotatedBitmap, bitmapInfo.w(), bitmapInfo.h(), !bitmapInfo.rotated());
        }
    }
}
