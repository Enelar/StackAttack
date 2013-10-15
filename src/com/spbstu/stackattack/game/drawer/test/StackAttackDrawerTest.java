package com.spbstu.stackattack.game.drawer.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.spbstu.stackattack.game.data.Player;
import com.spbstu.stackattack.game.data.StackAttackData;
import com.spbstu.stackattack.game.data.storeitem.StoreItem;
import com.spbstu.stackattack.game.drawer.StackAttackDrawer;
import com.spbstu.stackattack.game.support.SAGameBitmapLoader;
import com.spbstu.stackattack.game.support.SAOptions;

/**
 * Stack attack drawer implementation.
 *
 * @author bsi
 */
public class StackAttackDrawerTest extends StackAttackDrawer {
    private Paint fieldPaint = new Paint();

    /**
     * Class constructor.
     *
     * @param saData reference to a game data.
     */
    public StackAttackDrawerTest(final StackAttackData saData) {
        super(saData);

        fieldPaint.setColor(Color.WHITE);
        fieldPaint.setStyle(Paint.Style.STROKE);
        fieldPaint.setStrokeWidth(1.0f);
    }

    @Override
    public void draw(final Canvas c) {
        drawGameField(c);
        drawPlayer(c);
        drawCranes(c);
        drawStoreItems(c);
    }

    /**
     * Draw game field function.
     *
     * @param c canvas to draw into.
     */
    private void drawGameField(final Canvas c) {
        drawBitmap(c, SAGameBitmapLoader.bitmap(SAGameBitmapLoader.BitmapId.BACKGROUND), -0.1, 0,
                1.2 * SAOptions.FIELD_WIDTH, 1.2 * SAOptions.FIELD_HEIGHT);

        drawRect(c, 0, 0, SAOptions.FIELD_WIDTH, SAOptions.FIELD_HEIGHT, fieldPaint);

        for (int x = 0; x < SAOptions.FIELD_WIDTH; x++) {
            for (int y = 0; y < SAOptions.FIELD_HEIGHT; y++) {
                drawRect(c, x, y, 1, 1, fieldPaint);
            }
        }
    }

    /**
     * Draw player function.
     *
     * @param c canvas to draw into.
     */
    private void drawPlayer(final Canvas c) {
        Player player = saData.getPlayer();

        drawBitmap(c, SAGameBitmapLoader.bitmap(SAGameBitmapLoader.BitmapId.PLAYER), player.x, player.y, 1, 2);
    }

    /**
     * Draw cranes function.
     *
     * @param c canvas to draw into.
     */
    private void drawCranes(final Canvas c) {
/*
        Collection<Crane> cranes = saData.getCranes();

        for (final Crane crane : cranes) {

            drawRect(c, crane.x, crane.y - 0.06, 1.0, 0.14, cranePaint);
        }
*/
    }

    /**
     * Draw store items function.
     *
     * @param c canvas to draw into.
     */
    private void drawStoreItems(final Canvas c) {
        synchronized (saData.getStaticStoreItems()) {
            for (final StoreItem storeItem : saData.getStaticStoreItems()) {
                drawBitmap(c, getBoxBitmap(storeItem.type), storeItem.x, storeItem.y, 1, 1);
            }
        }

        synchronized (saData.getFallingStoreItems()) {
            for (final StoreItem storeItem : saData.getFallingStoreItems()) {
                drawBitmap(c, getBoxBitmap(storeItem.type), storeItem.x, storeItem.y, 1, 1);
            }
        }

        synchronized (saData.getMovingStoreItems()) {
            for (final StoreItem storeItem : saData.getMovingStoreItems()) {
                drawBitmap(c, getBoxBitmap(storeItem.type), storeItem.x, storeItem.y, 1, 1);
            }
        }
    }

    private Bitmap getBoxBitmap(final StoreItem.Type type) {
        switch (type) {
            case TYPE_1:
                return SAGameBitmapLoader.bitmap(SAGameBitmapLoader.BitmapId.BOX_1);
            case TYPE_2:
                return SAGameBitmapLoader.bitmap(SAGameBitmapLoader.BitmapId.BOX_2);
            case TYPE_3:
                return SAGameBitmapLoader.bitmap(SAGameBitmapLoader.BitmapId.BOX_3);
        }

        throw new IllegalArgumentException();
    }
}
