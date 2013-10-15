package com.spbstu.stackattack.game.data;

import com.spbstu.stackattack.game.data.event.GameOverEvent;
import com.spbstu.stackattack.game.data.storeitem.FallingStoreItems;
import com.spbstu.stackattack.game.data.storeitem.MovingStoreItems;
import com.spbstu.stackattack.game.data.storeitem.StaticStoreItems;
import com.spbstu.stackattack.game.data.storeitem.StoreItem;
import com.spbstu.stackattack.game.support.SAOptions;
import com.spbstu.stackattack.utils.DoubleUtils;

public class Player {
    private final ConversionRequestsContainer conversionRequestsContainer;

    private StaticStoreItems staticStoreItems;
    private FallingStoreItems fallingStoreItems;
    private MovingStoreItems movingStoreItems;

    public double x, y;
    public double vx = 0, vy = 0;
    public double dx = 0;

    public Player(final ConversionRequestsContainer conversionRequestsContainer, final double x, final double y) {
        this.conversionRequestsContainer = conversionRequestsContainer;

        this.x = x;
        this.y = y;
    }

    public void set(final StaticStoreItems staticStoreItems, final FallingStoreItems fallingStoreItems, final MovingStoreItems movingStoreItems) {
        this.staticStoreItems = staticStoreItems;
        this.fallingStoreItems = fallingStoreItems;
        this.movingStoreItems = movingStoreItems;
    }

    public void moveLeft() {
        if (DoubleUtils.zero(vx) && DoubleUtils.greater(x, 0)) {
            vx = -SAOptions.PLAYER_MOVE_SPEED;
            dx = -1;
        }
    }

    public void moveRight() {
        if (DoubleUtils.zero(vx) && DoubleUtils.less(x, SAOptions.FIELD_WIDTH - 1)) {
            vx = SAOptions.PLAYER_MOVE_SPEED;
            dx = 1;
        }
    }

    public void jump() {
        vy = SAOptions.PLAYER_JUMP_SPEED;
    }

    public void update(final double dt) {
        normalizeFuturePosition(dt);

        onSideHit(dt);
        onBottomHit(dt);

        y += vy * dt;

        x += vx * dt;
        dx -= vx * dt;

        if ((DoubleUtils.greater(dx, 0) && DoubleUtils.negative(vx)) || (DoubleUtils.less(dx, 0) && DoubleUtils.positive(vx))) {
            x += dx;

            x = DoubleUtils.round(x);

            dx = 0;
            vx = 0;
        }
    }

    private void onBottomHit(final double dt) {
        if (onBottomStaticItemsHit(dt)) {
            return;
        }

        vy -= SAOptions.PLAYER_FREE_FALL_ACCELERATION * dt;
    }

    private boolean onBottomStaticItemsHit(final double dt) {
        double newX = x + vx * dt;
        double newY = y + vy * dt;

        int playerLeft = (int)Math.floor(newX);
        int playerRight = playerLeft;

        if (DoubleUtils.hasFraction(newX)) {
            playerRight++;
        }

        int highestPoint = Math.max(staticStoreItems.height(playerLeft), staticStoreItems.height(playerRight));

        boolean playerBottomHitsStaticItems = highestPoint >= newY;

        if (DoubleUtils.negative(vy) && playerBottomHitsStaticItems) {
            vy = 0;
            y = highestPoint;
            return true;
        }

        return false;
    }

    private void onSideHit(final double dt) {
        if (DoubleUtils.zero(vx)) {
            return;
        }

        if (onSideStaticItemsHit(dt) || onSideFallingItemsHit(dt)) {
            vx = 0;
            dx = 0;
        }
    }

    private boolean onSideStaticItemsHit(final double dt) {
        double newX = x + vx * dt;

        if (DoubleUtils.negative(vx)) {
            int xn = (int)Math.floor(newX);

            if (DoubleUtils.greater(staticStoreItems.height(xn), y)) {
                x = xn + 1;
                return true;
            }
        } else if (DoubleUtils.positive(vx) && DoubleUtils.less(newX, SAOptions.FIELD_WIDTH - 1)) {
            int xn = (int)Math.floor(newX + 1);

            if (DoubleUtils.greater(staticStoreItems.height(xn), y)) {
                x = xn - 1;
                return true;
            }
        }

        return false;
    }

    private boolean onSideFallingItemsHit(final double dt) {
        double newX = x + vx * dt;

        for (StoreItem item : fallingStoreItems) {
            if ((DoubleUtils.less(newX - 1, item.x) && DoubleUtils.less(item.x, newX + 1)) &&
                (DoubleUtils.less(y - 1, item.y) && DoubleUtils.less(item.y, y + 2))) {
                return true;
            }
        }

        return false;
    }

    private void normalizeFuturePosition(final double dt) {
        double newX = x + vx * dt;

        if (DoubleUtils.less(newX, 0) || DoubleUtils.greater(newX, SAOptions.FIELD_WIDTH - 1)) {
            vx = 0;
        }
    }
}
