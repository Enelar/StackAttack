package bsiapi.bitmap.conversion;


import android.graphics.Bitmap;
import bsiapi.bitmap.BitmapInfo;
import bsiapi.bitmap.conversion.base.BitmapInfoTransformator;
import bsiapi.bitmap.conversion.base.BitmapInfoTransformatorDecorator;
import bsiapi.metrics.Metrics;

/**
 * Класс преобразования информации о битмапе.
 * Изменяет размеры битмапа до заданных в процентах от ширины экрана. Высота изменится пропорционально.
 *
 * @author bsi
 */
public final class BitmapInfoScaleTransformator extends BitmapInfoTransformatorDecorator {
    private final Metrics screenMetrics;

    private double nw = 1.0;

    /**
     * Конструктор класса.
     *
     * @param bitmapInfoTransformator внутренний преобразователь битмапа. Полученные преобразования будут наложены на преобразования внутреннего преобразователя.
     * @param screenMetrics размеры экрана.
     */
    public BitmapInfoScaleTransformator(final BitmapInfoTransformator bitmapInfoTransformator, final Metrics screenMetrics) {
        super(bitmapInfoTransformator);

        this.screenMetrics = screenMetrics;
    }

    /**
     * Функция установки новой ширины битмапа в процентах от ширины экрана.
     *
     * @param nw новая ширина битмапа в процентах от ширины экрана.
     */
    public void setNewWidth(final double nw) {
        this.nw = nw;
    }

    @Override
    protected BitmapInfo convertInner(final BitmapInfo bitmapInfo) {
        int newW = (int)(screenMetrics.w() * nw);
        int newH = (int)(bitmapInfo.h() * screenMetrics.w() * nw / bitmapInfo.w());

        final Bitmap scaledBitmap;

        if (bitmapInfo.rotated()) {
            scaledBitmap = Bitmap.createScaledBitmap(bitmapInfo.bitmap(), newH, newW, true);
        } else {
            scaledBitmap = Bitmap.createScaledBitmap(bitmapInfo.bitmap(), newW, newH, true);
        }

        return new BitmapInfo(scaledBitmap, newW, newH, bitmapInfo.rotated());
    }
}
