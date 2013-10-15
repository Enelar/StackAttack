package com.spbstu.stackattack.loading;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import bsiapi.bitmap.BitmapInfo;
import bsiapi.bitmap.BitmapLoader;
import bsiapi.bitmap.BitmapLoadersBase;
import bsiapi.bitmap.conversion.BitmapInfoRotationTransformator;
import bsiapi.bitmap.conversion.BitmapInfoScaleTransformator;
import bsiapi.coords.convertation.CoordinatesRotationConverter;
import bsiapi.coords.Coordinate;
import bsiapi.coords.convertation.base.CoordinatesConverter;
import bsiapi.drawer.Drawer;
import bsiapi.metrics.Metrics;
import bsiapi.metrics.MetricsConverter;
import bsiapi.text.Text;
import bsiapi.text.conversion.TextRotationTransformator;
import bsiapi.text.conversion.base.TextTransformator;
import com.spbstu.stackattack.R;
import com.spbstu.stackattack.menu.StackAttackMenuActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StackAttackLoadingActivity extends Activity {
    class MainView extends View {
        private final BitmapLoadersBase bitmapLoadersBase = BitmapLoadersBase.getInstance();

        private BitmapLoader bmLoader = null;
        private TextTransformator textTransformator = null;
        private Drawer drawer = null;

        private boolean loadingFinished = false;

        private Coordinate loadingBitmapVC, textVC;

        private Paint textPaint = new Paint();

        private int nPoints = 0;

        private Handler handler = new Handler();

        private final Timer screenUpdateTimer = new Timer();

        MainView(final Context context) {
            super(context);

            screenUpdateTimer.schedule(new TimerTask() {
                private final Runnable drawCaller = new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                };

                @Override
                public void run() {
                    if (loadingFinished) {
                        screenUpdateTimer.cancel();
                        screenUpdateTimer.purge();

                        Intent newIntent = new Intent(StackAttackLoadingActivity.this, StackAttackMenuActivity.class);
                        startActivity(newIntent);
                        finish();

                        bitmapLoadersBase.removeBitmapLoader(StackAttackLoadingActivity.class);

                        return;
                    }

                    handler.post(drawCaller);
                }
            }, 500, 500);

            textPaint.setTextSize(20.0f);
            textPaint.setAntiAlias(true);
        }

        @Override
        protected void onDraw(final Canvas c) {
            drawer.setCanvas(c);

            nPoints++;

            if (nPoints == 6) {
                nPoints = 0;
            }

            drawer.drawBitmap(bmLoader.get(R.drawable.loading_empty), loadingBitmapVC);

            String text = getString(R.string.loading_text);

            for (int i = 0; i < nPoints; i++) {
                text += ".";
            }

            drawer.drawText(textTransformator.transform(new Text(text)), textVC, textPaint);
        }

        @Override
        protected void onSizeChanged(final int w, final int h, final int oldW, final int oldH) {
            super.onSizeChanged(w, h, oldW, oldH);

            bmLoader = bitmapLoadersBase.addBitmapLoader(this.getClass(), getResources());

            final CoordinatesConverter coordinatesConverter = new CoordinatesRotationConverter(w, h);
            final MetricsConverter metricsConverter = new MetricsConverter(coordinatesConverter);

            final Metrics screenMetrics = new Metrics(w, h);
            final Metrics virtualMetrics = metricsConverter.convertToVirtual(screenMetrics);

            final BitmapInfoScaleTransformator bitmapInfoTransformator = new BitmapInfoScaleTransformator(new BitmapInfoRotationTransformator(screenMetrics), virtualMetrics);

            bitmapInfoTransformator.setNewWidth(1.0);
            bmLoader.load(R.drawable.loading_empty, bitmapInfoTransformator);

            textTransformator = new TextRotationTransformator(screenMetrics);
            drawer = new Drawer(coordinatesConverter);

            final BitmapInfo loadingBitmapInfo = bmLoader.get(R.drawable.loading_empty);

            loadingBitmapVC = new Coordinate(0, -(loadingBitmapInfo.h() - coordinatesConverter.vh()) / 2);

            textVC = new Coordinate(coordinatesConverter.vw() / 10, coordinatesConverter.vh() / 10);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    BitmapLoader menuBmLoader = bitmapLoadersBase.addBitmapLoader(StackAttackMenuActivity.class, getResources());

                    bitmapInfoTransformator.setNewWidth(1.0);
                    menuBmLoader.load(R.drawable.menu_empty, bitmapInfoTransformator);

                    bitmapInfoTransformator.setNewWidth(1.0 / 14.0);
                    menuBmLoader.load(R.drawable.info_button, bitmapInfoTransformator);
                    menuBmLoader.load(R.drawable.info_button_pressed, bitmapInfoTransformator);
                    menuBmLoader.load(R.drawable.sound_button, bitmapInfoTransformator);
                    menuBmLoader.load(R.drawable.sound_button_pressed, bitmapInfoTransformator);
                    menuBmLoader.load(R.drawable.options_button, bitmapInfoTransformator);
                    menuBmLoader.load(R.drawable.options_button_pressed, bitmapInfoTransformator);

                    bitmapInfoTransformator.setNewWidth(4.0 / 7.0);
                    menuBmLoader.load(R.drawable.button, bitmapInfoTransformator);
                    menuBmLoader.load(R.drawable.button_pressed, bitmapInfoTransformator);

                    loadingFinished = true;
                }
            }).start();
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setContentView(new MainView(this));
    }
}
