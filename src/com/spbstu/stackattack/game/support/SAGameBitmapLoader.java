package com.spbstu.stackattack.game.support;

import android.content.res.Resources;
import android.graphics.*;
import com.spbstu.stackattack.R;
import com.spbstu.stackattack.support.coords.SACoordinatesConverter;
import com.spbstu.stackattack.support.coords.SAScreenCoordinate;
import com.spbstu.stackattack.support.coords.SAVirtualCoordinate;

import java.util.HashMap;
import java.util.Map;

public class SAGameBitmapLoader {
    public static enum BitmapId {
        BOX_1(R.drawable.box_1, 1, 1),
        BOX_2(R.drawable.box_2, 1, 1),
        BOX_3(R.drawable.box_3, 1, 1),
        BOX_4(R.drawable.box_4, 1, 1),
        BOX_5(R.drawable.box_5, 1, 1),
        PLAYER(R.drawable.player, 1, 2),
        BACKGROUND(R.drawable.background, 1.2 * SAOptions.FIELD_WIDTH, 1.2 * SAOptions.FIELD_WIDTH);

        private final int id;
        private final double w, h;

        private BitmapId(final int id, final double w, final double h) {
            this.id = id;

            this.w = w;
            this.h = h;
        }
    }

    private static SACoordinatesConverter coordinatesConverter = SACoordinatesConverter.instance();

    private static Resources resources;

    private static final Map<BitmapId, Bitmap> bitmaps = new HashMap<BitmapId, Bitmap>();

    public static void set(final Resources resources) {
        SAGameBitmapLoader.resources = resources;
    }

    public static void resize(final int w, final int h) {
        SAVirtualCoordinate
                vc1 = SAGameCoordinatesConverter.fieldToVirtual(new SAGameCoordinatesConverter.FieldCoordinate(0, 0)),
                vc2 = SAGameCoordinatesConverter.fieldToVirtual(new SAGameCoordinatesConverter.FieldCoordinate(1, 0));
        SAScreenCoordinate
                sc1 = coordinatesConverter.virtualToScreen(vc1),
                sc2 = coordinatesConverter.virtualToScreen(vc2);

        final int size;

        if (w > h) {
            size = sc2.x - sc1.x;
        } else {
            size = sc1.y - sc2.y;
        }

        for (final BitmapId bitmapId : BitmapId.values()) {
            Bitmap bm = BitmapFactory.decodeResource(resources, bitmapId.id);

            bm = Bitmap.createScaledBitmap(bm, (int)(bitmapId.w * size), (int)(bitmapId.h * size), true);

            if (w < h) {
                Matrix matrix = new Matrix();

                matrix.postRotate(-90);

                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            }

            bitmaps.put(bitmapId, bm);
        }
    }

    public static Bitmap bitmap(final BitmapId bitmapId) {
        return bitmaps.get(bitmapId);
    }
}
