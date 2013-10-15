package bsiapi.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import bsiapi.bitmap.BitmapInfo;
import bsiapi.coords.Coordinate;
import bsiapi.coords.convertation.base.CoordinatesConverter;
import bsiapi.text.Text;

/**
 * Класс отрисовки графических примитивов.
 *
 * @author bsi
 */
public final class Drawer {
    private final CoordinatesConverter coordinatesConverter;

    private Canvas c = null;

    /**
     * Конструктор класса.
     *
     * @param coordinatesConverter преобразователь координат.
     */
    public Drawer(CoordinatesConverter coordinatesConverter) {
        this.coordinatesConverter = coordinatesConverter;
    }

    /**
     * Функция установки холста для рисования.
     *
     * @param c холст для рисования.
     */
    public void setCanvas(final Canvas c) {
        this.c = c;
    }

    /**
     * Функция отрисовки битмапа.
     *
     * @param bitmapInfo информация о битмапе.
     * @param vc виртуальные координаты текста.
     */
    public void drawBitmap(final BitmapInfo bitmapInfo, final Coordinate vc) {
        Coordinate bitmapVC = new Coordinate(vc.x() + bitmapInfo.w(), vc.y() + bitmapInfo.h());

        Coordinate sc = coordinatesConverter.virtualToScreen(bitmapVC);

        c.drawBitmap(bitmapInfo.bitmap(), (float)sc.x(), (float)sc.y(), null);
    }

    /**
     * Функция отрисовки битмапа.
     *
     * @param text информация о тексте для отрисовки.
     * @param vc виртуальные координаты текста.
     * @param paint информация о стиле рисования текста.
     */
    public void drawText(final Text text, final Coordinate vc, final Paint paint) {
        Coordinate sc = coordinatesConverter.virtualToScreen(vc);

        float oldTextSize = paint.getTextSize();

        paint.setTextSize((float)(oldTextSize * text.getSizeMultiplier()));

        if (text.getRotationAngle() != 0) {
            c.save();

            c.rotate((float)text.getRotationAngle(), (float)sc.x(), (float)sc.y());

            c.drawText(text.getText(), (float)sc.x(), (float)sc.y(), paint);

            c.restore();
        } else {
            c.drawText(text.getText(), (float)sc.x(), (float)sc.y(), paint);
        }

        paint.setTextSize(oldTextSize);
    }
}
