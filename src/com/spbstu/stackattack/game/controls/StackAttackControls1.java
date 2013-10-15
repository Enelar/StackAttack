package com.spbstu.stackattack.game.controls;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.spbstu.stackattack.game.data.Player;
import com.spbstu.stackattack.game.data.StackAttackData;
import com.spbstu.stackattack.game.support.SAGameCoordinatesConverter;
import com.spbstu.stackattack.game.support.SAOptions;
import com.spbstu.stackattack.support.coords.SACoordinatesConverter;
import com.spbstu.stackattack.support.coords.SAScreenCoordinate;
import com.spbstu.stackattack.support.coords.SAVirtualCoordinate;

public class StackAttackControls1 extends StackAttackControls {
    private class TouchInfo {
        public double x, y;
        public int touchId;

        private TouchInfo(final double x, final double y, final int touchId) {
            this.x = x;
            this.y = y;
            this.touchId = touchId;
        }
    }

    private enum Direction {
        NONE, LEFT, RIGHT
    }

    private final double TOUCH_DELAY = 0.1;

    private final int MAX_TOUCHES_AMOUNT = 10;

    private TouchInfo [] touchesInfo = new TouchInfo [MAX_TOUCHES_AMOUNT];
    private int currentTouchesAmount = 0;

    private double t;

    private Direction direction = Direction.NONE;

    private GestureDetector doubleTapDetector;

    public StackAttackControls1(final Context context, final StackAttackData saData) {
        super(saData);

        doubleTapDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(final MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(final MotionEvent e) {
                saData.jump();
                return true;
            }
        });
    }

    private SACoordinatesConverter coordinatesConverter = SACoordinatesConverter.instance();

    @Override
    public boolean touch(final MotionEvent e) {
        doubleTapDetector.onTouchEvent(e);

        int pointerIndex = e.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT;
        int pointerId = e.getPointerId(pointerIndex);

        SAVirtualCoordinate vTouchCoordinates
                = coordinatesConverter.screenToVirtual(new SAScreenCoordinate((int)e.getX(pointerIndex), (int)e.getY(pointerIndex)));
        SAGameCoordinatesConverter.FieldCoordinate fTouchCoordinates = SAGameCoordinatesConverter.virtualToField(vTouchCoordinates);

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                t = 0;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (currentTouchesAmount == MAX_TOUCHES_AMOUNT) {
                    break;
                }

                touchesInfo[currentTouchesAmount++] = new TouchInfo(fTouchCoordinates.x, fTouchCoordinates.y, pointerId);
                break;
            case MotionEvent.ACTION_UP:
                t = 0;
            case MotionEvent.ACTION_POINTER_UP:
                for (int i = 0; i < currentTouchesAmount; i++) {
                    if (touchesInfo[i].touchId == pointerId) {
                        for (int j = i + 1; j < currentTouchesAmount; j++) {
                            touchesInfo[j - 1] = touchesInfo[j];
                        }

                        if (i == 0) {
                            direction = Direction.NONE;
                        }

                        currentTouchesAmount--;

                        break;
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < currentTouchesAmount; i++) {
                    if (touchesInfo[i].touchId == pointerId) {
                        if (i == 0) {
                            direction = Direction.NONE;
                        }

                        touchesInfo[i].x = fTouchCoordinates.x;
                        touchesInfo[i].y = fTouchCoordinates.y;
                    }
                }

                break;
        }

        return true;
    }

    @Override
    public void update(final double dt) {
        t += dt;

        if (t >= TOUCH_DELAY) {
            if (currentTouchesAmount != 0) {
                if (direction == Direction.NONE) {
                    double touchX = touchesInfo[0].x;

                    if (touchX < SAOptions.FIELD_WIDTH / 4) {
                        direction = Direction.LEFT;
                        saData.moveLeft();
                    } else if (touchX > 3 * SAOptions.FIELD_WIDTH / 4) {
                        direction = Direction.RIGHT;
                        saData.moveRight();
                    } else {
                        Player player = saData.getPlayer();

                        if (touchX < player.x) {
                            direction = Direction.LEFT;
                            saData.moveLeft();
                        } else {
                            direction = Direction.RIGHT;
                            saData.moveRight();
                        }
                    }
                } else {
                    switch (direction) {
                        case LEFT:
                            saData.moveLeft();
                            break;
                        case RIGHT:
                            saData.moveRight();
                            break;
                    }
                }
            }
        }
    }
}
