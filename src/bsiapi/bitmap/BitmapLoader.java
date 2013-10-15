package bsiapi.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import bsiapi.bitmap.conversion.base.BitmapInfoTransformator;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс загрузки битмапов.
 *
 * @author bsi
 */
public final class BitmapLoader {
    private final Resources resources;

    private final Map<Integer, BitmapInfo> bitmapsInfo = new HashMap<Integer, BitmapInfo>();

    /**
     * Конструктор класса.
     *
     * @param resources медиа-ресурсы приложения.

     */
    BitmapLoader(final Resources resources) {
        this.resources = resources;
    }

    /**
     * Функция загрузки битмапаю
     *
     * @param id идентификатор ресурса битмапа.
     * @param bitmapInfoTransformator преобразователь информации о битмапе.
     */
    public void load(final int id, final BitmapInfoTransformator bitmapInfoTransformator) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, id);

        bitmapsInfo.put(id, bitmapInfoTransformator.convert(new BitmapInfo(bitmap)));
    }

    /**
     * Функция получения информации о загруженном битмапе.
     *
     * @param id идентификатор ресурса битмапа.
     *
     * @return информация о загруженном битмапе.
     */
    public BitmapInfo get(final int id) {
        return bitmapsInfo.get(id);
    }
}
