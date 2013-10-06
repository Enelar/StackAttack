package com.spbstu.stackattack.game.data;

import com.spbstu.stackattack.game.support.StackAttackOptions;
import com.spbstu.stackattack.utils.DoubleUtils;

import java.util.*;

/**
 * ToDo list:
 *
 * (1). Добавить движение рабочего (+)
 *  2 . Добавить возможность рабочего двигать блоки
 */

/**
 * Stack attack game data representation class.
 *
 * @author bsi
 */
public class StackAttackData {
    private final List<StoreItem> fallingStoreItems = Collections.synchronizedList(new ArrayList<StoreItem>());
    private final StaticStoreItems staticStoreItems = new StaticStoreItems();
    private final List<StoreItem> movedStoreItems = Collections.synchronizedList(new ArrayList<StoreItem>());
    private final Player player = new Player(0, 0);

    private Random r = new Random();
    private double t = 0.0;

    public Collection<StoreItem> getFallingStoreItems() {
        return fallingStoreItems;
    }

    public Collection<StoreItem> getStaticStoreItems() {
        return staticStoreItems;
    }

    public Collection<StoreItem> getMovedStoreItems() {
        return movedStoreItems;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Update stack attack game data function.
     *
     * @param dt time passed since last update.
     */
    public void update(final double dt) {
        t += dt;

        if (t > StackAttackOptions.CRANE_OBJECT_GENERATION_FREQUENCY) {
            t -= StackAttackOptions.CRANE_OBJECT_GENERATION_FREQUENCY;

            StoreItem si = new StoreItem(r.nextInt(StackAttackOptions.FIELD_WIDTH), StackAttackOptions.FIELD_HEIGHT - 1);

            si.vy = -StackAttackOptions.STORE_ITEM_START_SPEED;

            fallingStoreItems.add(si);
        }

        for (Iterator<StoreItem> iterator = fallingStoreItems.iterator(); iterator.hasNext();) {
            if (!updateFallingStoreItem(iterator, dt)) {
                return;
            }
        }

        for (Iterator<StoreItem> iterator = movedStoreItems.iterator(); iterator.hasNext();) {
            updateMovedStoreItem(iterator, dt);
        }

        updatePlayer(dt);

        for (int x = 0; x < StackAttackOptions.FIELD_WIDTH; x++) {
            if (staticStoreItems.height(x) == StackAttackOptions.FIELD_HEIGHT) {
                staticStoreItems.clear();
                break;
            }
        }
    }

    private boolean updateFallingStoreItem(final Iterator<StoreItem> itemIterator, final double dt) {
        StoreItem storeItem = itemIterator.next();

        /* Collide with borders */
        if (storeItem.y < 0) {
            storeItem.vy = 0;
            storeItem.y = 0;

            itemIterator.remove();
            staticStoreItems.add(storeItem);
        } else {
            /* Collide with player */
            boolean hasHCollisionWithPlayer = DoubleUtils.less(player.x - 1, storeItem.x) && DoubleUtils.less(storeItem.x, player.x + 1);

            if (hasHCollisionWithPlayer) {
                boolean hasVCollision = DoubleUtils.less(player.y - 1, storeItem.y) && DoubleUtils.less(storeItem.y, player.y + 2);

                if (hasVCollision) {
                    gameOverAction();
                    return false;
                }
            }

            /* Collide with static store items */
            double h = staticStoreItems.height((int)storeItem.x);

            if (storeItem.y < h) {
                storeItem.y = h;
                storeItem.vy = 0;

                itemIterator.remove();
                staticStoreItems.add(storeItem);
            }

            /* Collide with moving store items */
            for (final StoreItem movedStoreItem : movedStoreItems) {
                boolean hasHCollisionWithMovedStoreItem =
                        DoubleUtils.less(movedStoreItem.x - 1, storeItem.x) && DoubleUtils.less(storeItem.x, movedStoreItem.x + 1);

                if (hasHCollisionWithMovedStoreItem && storeItem.y < movedStoreItem.y + 1) {
                    return true;
                }
            }

            storeItem.y += storeItem.vy * dt;
        }

        return true;
    }

    private void updateMovedStoreItem(final Iterator<StoreItem> itemIterator, final double dt) {
        StoreItem storeItem = itemIterator.next();

        storeItem.x += storeItem.vx * dt;
        storeItem.dx -= storeItem.vx * dt;

        if ((storeItem.dx >= 0 && storeItem.vx < 0) || (storeItem.dx <= 0 && storeItem.vx > 0)){
            storeItem.x += storeItem.dx;

            storeItem.x = DoubleUtils.round(storeItem.x);

            storeItem.dx = 0;
            storeItem.vx = 0;

            int storeItemLeft = (int)Math.floor(storeItem.x);
            int storeItemRight = storeItemLeft;

            if (DoubleUtils.hasFraction(storeItem.x)) {
                storeItemRight++;
            }

            boolean isStoreItemOnGround =
                    staticStoreItems.height(storeItemLeft) >= storeItem.y ||
                    staticStoreItems.height(storeItemRight) >= storeItem.y;

            if (isStoreItemOnGround) {
                storeItem.vy = 0;

                itemIterator.remove();
                staticStoreItems.add(storeItem);
            } else {
                storeItem.vy = -StackAttackOptions.STORE_ITEM_START_SPEED;
            }
        }

        if (!DoubleUtils.zero(storeItem.vy)) {
            double h = staticStoreItems.height((int)storeItem.x);

            if (storeItem.y < h) {
                storeItem.y = h;
                storeItem.vy = 0;

                itemIterator.remove();
                staticStoreItems.add(storeItem);
            }

            storeItem.y += storeItem.vy * dt;
        }
    }

    private void updatePlayer(final double dt) {
        int playerLeft = (int)Math.floor(player.x);
        int playerRight = playerLeft;

        if (DoubleUtils.hasFraction(player.x)) {
            playerRight++;
        }

        boolean isPlayerOnGround =
                staticStoreItems.height(playerLeft) >= player.y ||
                staticStoreItems.height(playerRight) >= player.y;

        int highestPoint = Math.max(staticStoreItems.height(playerLeft), staticStoreItems.height(playerRight));

        if (player.vy > 0 || !isPlayerOnGround) {
            player.vy -= StackAttackOptions.PLAYER_FREE_FALL_ACCELERATION * dt;
        } else {
            player.vy = 0;
            player.y = highestPoint;
        }

        player.x += player.vx * dt;
        player.dx -= player.vx * dt;

        if ((player.dx >= 0 && player.vx < 0) || (player.dx <= 0 && player.vx > 0)){
            player.x += player.dx;

            player.x = DoubleUtils.round(player.x);

            player.dx = 0;
            player.vx = 0;
        }

        player.y += player.vy * dt;
    }

    /**
     * Order player to move left.
     */
    public void moveLeft() {
        if (player.vx == 0 && DoubleUtils.greaterOrEquals(player.x, 1)) {
            int playerLeft = (int)DoubleUtils.round(player.x);

            for (Iterator<StoreItem> iterator = fallingStoreItems.iterator(); iterator.hasNext();) {
                StoreItem storeItem = iterator.next();

                boolean hasVCollision = DoubleUtils.less(player.y - 1, storeItem.y) && DoubleUtils.less(storeItem.y, player.y + 2);

                if (hasVCollision && DoubleUtils.equals(storeItem.x, player.x - 1)) {
                    int h = (int)Math.floor(storeItem.y);

                    if (playerLeft - 1 > 0 && h >= staticStoreItems.height(playerLeft - 1)) {
                        iterator.remove();

                        storeItem.vx = -StackAttackOptions.PLAYER_MOVE_SPEED;
                        storeItem.dx = -1.0;

                        movedStoreItems.add(storeItem);
                    } else {
                        return;
                    }
                }
            }

            int playerBottom = (int)Math.floor(player.y);
            int h = staticStoreItems.height(playerLeft - 1);

            if (h > playerBottom) {
                if (h == playerBottom + 1 && playerLeft - 1 > 0 && h > staticStoreItems.height(playerLeft - 2)) {
                    StoreItem storeItemToMove = staticStoreItems.getAndRemoveFromTop(playerLeft - 1);

                    staticStoreItems.remove(storeItemToMove);

                    storeItemToMove.vx = -StackAttackOptions.PLAYER_MOVE_SPEED;
                    storeItemToMove.dx = -1.0;

                    movedStoreItems.add(storeItemToMove);
                } else {
                    return;
                }
            }

            if (staticStoreItems.height((int)DoubleUtils.round(player.x) - 1) > player.y) {
                return;
            }

            player.moveLeft();
        }
    }

    /**
     * Order player to move right.
     */
    public void moveRight() {
        if (player.vx == 0 && DoubleUtils.less(player.x, StackAttackOptions.FIELD_WIDTH - 1)) {
            for (StoreItem storeItem : fallingStoreItems) {
                boolean hasVCollision = DoubleUtils.less(player.y - 1, storeItem.y) && DoubleUtils.less(storeItem.y, player.y + 2);

                if (hasVCollision && DoubleUtils.equals(storeItem.x, player.x + 1)) {
                    return;
                }
            }

            int playerRight = (int)DoubleUtils.round(player.x) + 1;
            int playerBottom = (int)Math.floor(player.y);
            int h = staticStoreItems.height(playerRight);

            if (h > playerBottom) {
                if (h == playerBottom + 1 && playerRight < StackAttackOptions.FIELD_WIDTH - 1 && h > staticStoreItems.height(playerRight + 1)) {
                    StoreItem storeItemToMove = staticStoreItems.getAndRemoveFromTop(playerRight);

                    staticStoreItems.remove(storeItemToMove);

                    storeItemToMove.vx = StackAttackOptions.PLAYER_MOVE_SPEED;
                    storeItemToMove.dx = 1.0;

                    movedStoreItems.add(storeItemToMove);
                } else {
                    return;
                }
            }

            player.moveRight();
        } else if (player.vx < 0) {
            player.moveBack();
        }
    }

    /**
     * Order player to jump.
     */
    public void jump() {
        int playerLeft = (int)Math.floor(player.x);
        int playerRight = playerLeft;

        if (DoubleUtils.hasFraction(player.x)) {
            playerRight++;
        }

        boolean isPlayerOnGround =
                DoubleUtils.equals(staticStoreItems.height(playerLeft), player.y) ||
                DoubleUtils.equals(staticStoreItems.height(playerRight), player.y);

        if (isPlayerOnGround) {
            player.jump();
        }
    }

    private void gameOverAction() {
        fallingStoreItems.clear();
        staticStoreItems.clear();
    }
}
