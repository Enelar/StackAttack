package com.spbstu.stackattack.game.drawer.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.spbstu.stackattack.game.data.Player;
import com.spbstu.stackattack.game.data.StackAttackData;
import com.spbstu.stackattack.game.data.StoreItem;
import com.spbstu.stackattack.game.drawer.StackAttackDrawer;
import com.spbstu.stackattack.game.support.StackAttackOptions;

/**
 * Stack attack drawer implementation.
 *
 * @author bsi
 */
public class StackAttackDrawerTest extends StackAttackDrawer {
    private Paint fieldPaint = new Paint();
    private Paint playerPaint = new Paint();
    //private Paint cranePaint = new Paint();
    private Paint staticStoreItemPaint = new Paint();
    private Paint fallingStoreItemPaint = new Paint();
    private Paint movedStoreItemPaint = new Paint();

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

        playerPaint.setColor(Color.BLUE);

        //cranePaint.setColor(Color.BLUE);

        staticStoreItemPaint.setColor(Color.GREEN);
        fallingStoreItemPaint.setColor(Color.RED);
        movedStoreItemPaint.setColor(Color.YELLOW);
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
        drawRect(c, 0, 0, StackAttackOptions.FIELD_WIDTH, StackAttackOptions.FIELD_HEIGHT, fieldPaint);

        for (int x = 0; x < StackAttackOptions.FIELD_WIDTH; x++) {
            for (int y = 0; y < StackAttackOptions.FIELD_HEIGHT; y++) {
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

        drawRect(c, player.x, player.y, 0.97, StackAttackOptions.PLAYER_HEIGHT - 0.03, playerPaint);
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
                drawRect(c, storeItem.x, storeItem.y, 0.97, 0.97, staticStoreItemPaint);
            }
        }

        synchronized (saData.getFallingStoreItems()) {
            for (final StoreItem storeItem : saData.getFallingStoreItems()) {
                drawRect(c, storeItem.x, storeItem.y, 0.97, 0.97, fallingStoreItemPaint);
            }
        }

        synchronized (saData.getMovedStoreItems()) {
            for (final StoreItem storeItem : saData.getMovedStoreItems()) {
                drawRect(c, storeItem.x, storeItem.y, 0.97, 0.97, movedStoreItemPaint);
            }
        }
    }
}
