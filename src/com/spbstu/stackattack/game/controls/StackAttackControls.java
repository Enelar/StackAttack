package com.spbstu.stackattack.game.controls;

import android.view.MotionEvent;
import com.spbstu.stackattack.game.data.StackAttackData;

/**
 * Stack attack controls representation class.
 *
 * @author bsi
 */
public abstract class StackAttackControls {
    protected final StackAttackData saData;

    /**
     * Class constructor.
     *
     * @param saData reference to a game data.
     */
    protected StackAttackControls(final StackAttackData saData) {
        this.saData = saData;
    }

    /**
     * Handle touch event function.
     *
     * @param e information about event.
     */
    public abstract boolean touch(final MotionEvent e);

    public abstract void update(final double dt);
}
