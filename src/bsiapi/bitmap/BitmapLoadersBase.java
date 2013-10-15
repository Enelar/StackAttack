package bsiapi.bitmap;

import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Контейнер загрузчиков битмапов.
 *
 * @author bsi
 */
public final class BitmapLoadersBase {
    private static BitmapLoadersBase instance = new BitmapLoadersBase();

    private Map<Class, BitmapLoader> bitmapLoaders = new HashMap<Class, BitmapLoader>();

    /**
     * Функция получения экземпляра объекта.
     *
     * @return экземпляр объекта.
     */
    public static BitmapLoadersBase getInstance() {
        return instance;
    }

    /**
     * Функция добавления загрузчика битмапа.
     *
     * @param cls класс, к которому привязывается загрузчик битмапа.
     * @param resources медиа-ресурсы приложения.
     *
     * @return созданный загрузчик битмапа.
     */
    public synchronized BitmapLoader addBitmapLoader(final Class cls, final Resources resources) {
        BitmapLoader bitmapLoader = new BitmapLoader(resources);

        bitmapLoaders.put(cls, bitmapLoader);

        return bitmapLoader;
    }

    /**
     * Функция получения загрузчика битмапа.
     *
     * @param cls класс, к которому привязан загрузчик битмапа.
     *
     * @return загрузчик битмапа.
     */
    public BitmapLoader getBitmapLoader(final Class cls) {
        return bitmapLoaders.get(cls);
    }

    /**
     * Функция удаления загрузчика битмапа.
     *
     * @param cls класс, к которому привязан загрузчик битмапа.
     */
    public void removeBitmapLoader(final Class cls) {
        bitmapLoaders.remove(cls);
    }

    private BitmapLoadersBase() {}
}
