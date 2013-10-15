package com.spbstu.stackattack.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import com.spbstu.stackattack.R;
import com.spbstu.stackattack.support.coords.SACoordinatesConverter;
import com.spbstu.stackattack.utils.TimeUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Stack attack application activity.
 *
 * @author bsi
 */
public class StackAttackGameActivity extends Activity {
    private boolean pauseOn = false;

    /**
     * Stack attack game view.
     *
     * @author bsi
     */
    class StackAttackView extends View {
        private final SACoordinatesConverter coordinatesConverter = SACoordinatesConverter.instance();

        private final long UPDATE_TIME_IN_MILLS = 20;

        private final StackAttackGame game = new StackAttackGame(StackAttackGameActivity.this);

        private Handler handler = new Handler();

        private final Timer gameUpdateTimer = new Timer();

        /**
         * Class constructor.
         *
         * @param context interface about global application information.
         */
        StackAttackView(final Context context) {
            super(context);

            gameUpdateTimer.schedule(new TimerTask() {
                private final Runnable drawCaller = new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                };

                private long lastUpdateTime = -1;

                @Override
                public void run() {
                    final long currentTime = System.currentTimeMillis();

                    if (!pauseOn) {
                        if (lastUpdateTime == -1) {
                            game.update(TimeUtils.millsToSec(UPDATE_TIME_IN_MILLS));
                        } else {
                            long dt = currentTime - lastUpdateTime;

                            while (dt > UPDATE_TIME_IN_MILLS) {
                                game.update(TimeUtils.millsToSec(UPDATE_TIME_IN_MILLS));

                                dt -= UPDATE_TIME_IN_MILLS;
                            }

                            game.update(TimeUtils.millsToSec(dt));

                            handler.post(drawCaller);
                        }
                    }

                    lastUpdateTime = currentTime;
                }
            }, UPDATE_TIME_IN_MILLS, UPDATE_TIME_IN_MILLS);
        }

        @Override
        protected void onDraw(final Canvas c) {
            super.onDraw(c);

            game.draw(c);
        }

        @Override
        protected void onSizeChanged(final int w, final int h, final int oldW, final int oldH) {
            super.onSizeChanged(w, h, oldW, oldH);

            coordinatesConverter.resize(w, h);

            game.resize(w, h);
        }

        @Override
        public boolean onTouchEvent(final MotionEvent e) {
            return game.touch(e);
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setContentView(new StackAttackView(this));
    }

    @Override
    protected void onPause() {
        super.onPause();

        pauseOn = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        pauseOn = false;
    }
}
