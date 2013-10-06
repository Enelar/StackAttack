package com.spbstu.stackattack.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import com.spbstu.stackattack.game.controls.StackAttackControls;
import com.spbstu.stackattack.game.controls.bypress.StackAttackControls1;
import com.spbstu.stackattack.game.data.StackAttackData;
import com.spbstu.stackattack.game.drawer.StackAttackDrawer;
import com.spbstu.stackattack.game.drawer.test.StackAttackDrawerTest;
import com.spbstu.stackattack.game.support.StackAttackCoordinatesConverter;

/**
 * Stack attack game representation class.
 *
 * @author bsi
 */
public class StackAttackGame {
    private StackAttackData saData = new StackAttackData();
    private StackAttackDrawer saDrawer = new StackAttackDrawerTest(saData);
    private StackAttackControls saControls;

    private StackAttackCoordinatesConverter converter = StackAttackCoordinatesConverter.getInstance();

    public StackAttackGame(final Context context) {
        saControls = new StackAttackControls1(context, saData);
    }

    /**
     * Draw stack attack game data function.
     *
     * @param c canvas to draw into.
     */
    public void draw(final Canvas c) {
        saDrawer.draw(c);
    }

    /**
     * Response on screen user area resize.
     *
     * @param w new screen width.
     * @param h new screen height.
     */
    public void resize(final int w, final int h) {
        converter.resize(w, h);
    }

    /**
     * Update stack attack game data function.
     *
     * @param dt time passed since last update.
     */
    public void update(final double dt) {
        saControls.update(dt);
        saData.update(dt);
    }

    /**
     * Handle touch event function.
     *
     * @param e information about event.
     */
    public boolean touch(final MotionEvent e) {
        return saControls != null && saControls.touch(e);
    }
}
