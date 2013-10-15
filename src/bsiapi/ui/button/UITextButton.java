package bsiapi.ui.button;

import android.graphics.Paint;
import android.graphics.Rect;
import bsiapi.bitmap.BitmapInfo;
import bsiapi.coords.Coordinate;
import bsiapi.drawer.Drawer;
import bsiapi.text.Text;
import bsiapi.ui.element.UIElementTouchListener;

/**
 * Класс, описывающий кнопку, содержащую текст.
 *
 * @author bsi
 */
public class UITextButton extends UIButton {
    private final Text text;
    private final Paint textPaint;

    /**
     * Конструктор класса.
     *
     * @param touchListener объект реакции на нажатие.
     * @param bitmapFree битмап, отрисовывающийся, когда кнопка не нажата.
     * @param bitmapPressed  битмап, отрисовывающийся, когда кнопка нажата.
     * @param vc виртуальные координаты кнопки.
     * @param text текст для отрисовки.
     * @param textPaint стиль отрисовки текста.
     */
    public UITextButton(final UIElementTouchListener touchListener, final BitmapInfo bitmapFree, final BitmapInfo bitmapPressed, final Coordinate vc, final Text text, final Paint textPaint) {
        super(touchListener, bitmapFree, bitmapPressed, vc);

        this.text = text;
        this.textPaint = textPaint;
    }

    @Override
    public void draw(final Drawer drawer) {
        super.draw(drawer);

        Rect textBounds = new Rect();
        textPaint.getTextBounds(text.getText(), 0, text.getText().length(), textBounds);

        double textW = (textBounds.right - textBounds.left) * text.getSizeMultiplier();
        double textH = (textBounds.bottom - textBounds.top) * text.getSizeMultiplier();

        Coordinate textVC = new Coordinate(vc.x() + (bitmapVW() - textW) / 2, vc.y() + (bitmapVH() - textH) / 2);

        drawer.drawText(text, textVC, textPaint);
    }
}
