package com.spbstu.stackattack.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import bsiapi.bitmap.BitmapInfo;
import bsiapi.bitmap.BitmapLoader;
import bsiapi.bitmap.BitmapLoadersBase;
import bsiapi.coords.convertation.CoordinatesRotationConverter;
import bsiapi.coords.Coordinate;
import bsiapi.coords.convertation.base.CoordinatesConverter;
import bsiapi.drawer.Drawer;
import bsiapi.metrics.Metrics;
import bsiapi.text.Text;
import bsiapi.text.conversion.TextRotationTransformator;
import bsiapi.text.conversion.base.TextTransformator;
import bsiapi.ui.button.UITextButton;
import bsiapi.ui.button.UIButton;
import bsiapi.ui.element.UIElement;
import bsiapi.ui.element.UIElementTouchListener;
import bsiapi.ui.element.UITouchInfo;
import com.spbstu.stackattack.R;
import com.spbstu.stackattack.game.StackAttackGameActivity;

import java.util.ArrayList;
import java.util.Collection;

public class StackAttackMenuActivity extends Activity {
    class MainView extends View {
        private final BitmapLoadersBase bitmapLoadersBase = BitmapLoadersBase.getInstance();

        private BitmapLoader bmLoader = null;

        private CoordinatesConverter coordinatesConverter = null;
        private TextTransformator textTransformator = null;

        private Drawer drawer = null;

        private Coordinate menuBitmapVC;

        private Collection<UIElement> uiElements = new ArrayList<UIElement>();

        MainView(final Context context) {
            super(context);
        }

        @Override
        protected void onDraw(final Canvas c) {
            drawer.setCanvas(c);

            drawer.drawBitmap(bmLoader.get(R.drawable.menu_empty), menuBitmapVC);

            for (final UIElement uiElement : uiElements) {
                uiElement.draw(drawer);
            }
        }

        @Override
        protected void onSizeChanged(final int w, final int h, final int oldW, final int oldH) {
            super.onSizeChanged(w, h, oldW, oldH);

            bmLoader = bitmapLoadersBase.getBitmapLoader(StackAttackMenuActivity.class);

            coordinatesConverter = new CoordinatesRotationConverter(w, h);

            textTransformator = new TextRotationTransformator(new Metrics(w, h));
            drawer = new Drawer(coordinatesConverter);

            final BitmapInfo menuBitmapInfo = bmLoader.get(R.drawable.menu_empty);

            menuBitmapVC = new Coordinate(0, -(menuBitmapInfo.h() - coordinatesConverter.vh()) / 2);

            initUIElements(coordinatesConverter, textTransformator);
        }

        @Override
        public boolean onTouchEvent(final MotionEvent e) {
            final UITouchInfo touchInfo = new UITouchInfo(e, coordinatesConverter);

            for (final UIElement uiElement : uiElements) {
                uiElement.touch(touchInfo);
            }

            invalidate();

            return true;
        }

        private void initUIElements(final CoordinatesConverter coordinatesConverter, final TextTransformator textTransformator) {
            uiElements.clear();

            UIElementTouchListener emptyTouchListener = new UIElementTouchListener() {
                @Override
                public void onTouch(UITouchInfo touchInfo) {
                }
            };

            /* Вспомогательные кнопки */

            /* Кнопка 'звук' */
            final BitmapInfo freeSoundButtonBitmapInfo = bmLoader.get(R.drawable.sound_button);
            final BitmapInfo pressedSoundButtonBitmapInfo = bmLoader.get(R.drawable.sound_button_pressed);

            uiElements.add(new UIButton(emptyTouchListener, freeSoundButtonBitmapInfo, pressedSoundButtonBitmapInfo,
                    new Coordinate(0, coordinatesConverter.vh() - freeSoundButtonBitmapInfo.h())));

            /* Кнопка 'опции' */
            final BitmapInfo freeOptionsButtonBitmapInfo = bmLoader.get(R.drawable.options_button);
            final BitmapInfo pressedOptionsButtonBitmapInfo = bmLoader.get(R.drawable.options_button_pressed);

            uiElements.add(new UIButton(emptyTouchListener, freeOptionsButtonBitmapInfo, pressedOptionsButtonBitmapInfo,
                    new Coordinate(0, 0)));

            /* Кнопка 'информация' */
            final BitmapInfo freeInfoButtonBitmapInfo = bmLoader.get(R.drawable.info_button);
            final BitmapInfo pressedInfoButtonBitmapInfo = bmLoader.get(R.drawable.info_button_pressed);

            uiElements.add(new UIButton(emptyTouchListener, freeInfoButtonBitmapInfo, pressedInfoButtonBitmapInfo,
                    new Coordinate(coordinatesConverter.vw() - freeInfoButtonBitmapInfo.w(), coordinatesConverter.vh() - freeInfoButtonBitmapInfo.h())));

            /* Основные кнопки */

            final Paint textPaint = new Paint();

            textPaint.setTextSize(20.0f);
            textPaint.setAntiAlias(true);
            textPaint.setTypeface(Typeface.SANS_SERIF);

            final BitmapInfo freeButtonBitmapInfo = bmLoader.get(R.drawable.button);
            final BitmapInfo pressedButtonBitmapInfo = bmLoader.get(R.drawable.button_pressed);

            /* Кнопка 'новая игра' */

            uiElements.add(new UITextButton(new UIElementTouchListener() {
                        @Override
                        public void onTouch(UITouchInfo touchInfo) {
                            Intent newIntent = new Intent(StackAttackMenuActivity.this, StackAttackGameActivity.class);
                            startActivity(newIntent);
                            finish();
                        }
                    }, freeButtonBitmapInfo, pressedButtonBitmapInfo,
                    new Coordinate((coordinatesConverter.vw() - freeButtonBitmapInfo.w()) / 2, 9 * coordinatesConverter.vh() / 10 - 1.0 * freeButtonBitmapInfo.h()),
                    textTransformator.transform(new Text(getString(R.string.new_game_button_text))), textPaint));

            /* Кнопка 'продолжить' */

            uiElements.add(new UITextButton(emptyTouchListener, freeButtonBitmapInfo, pressedButtonBitmapInfo,
                    new Coordinate((coordinatesConverter.vw() - freeButtonBitmapInfo.w()) / 2, 9 * coordinatesConverter.vh() / 10 - 2.2 * freeButtonBitmapInfo.h()),
                    textTransformator.transform(new Text(getString(R.string.continue_game_button_text))), textPaint));

            /* Кнопка 'рекорды' */

            uiElements.add(new UITextButton(emptyTouchListener, freeButtonBitmapInfo, pressedButtonBitmapInfo,
                    new Coordinate((coordinatesConverter.vw() - freeButtonBitmapInfo.w()) / 2, 9 * coordinatesConverter.vh() / 10 - 3.4 * freeButtonBitmapInfo.h()),
                    textTransformator.transform(new Text(getString(R.string.highscores_button_text))), textPaint));

            /* Кнопка 'выйти' */

            uiElements.add(new UITextButton(new UIElementTouchListener() {
                        @Override
                        public void onTouch(UITouchInfo touchInfo) {
                            finish();
                        }
                    }, freeButtonBitmapInfo, pressedButtonBitmapInfo,
                    new Coordinate((coordinatesConverter.vw() - freeButtonBitmapInfo.w()) / 2, 9 * coordinatesConverter.vh() / 10 - 4.6 * freeButtonBitmapInfo.h()),
                    textTransformator.transform(new Text(getString(R.string.exit_button_text))), textPaint));
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setContentView(new MainView(this));
    }
}