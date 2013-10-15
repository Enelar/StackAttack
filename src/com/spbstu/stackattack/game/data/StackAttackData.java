package com.spbstu.stackattack.game.data;

import com.spbstu.stackattack.game.data.event.GameOverEvent;
import com.spbstu.stackattack.game.data.storeitem.FallingStoreItems;
import com.spbstu.stackattack.game.data.storeitem.MovingStoreItems;
import com.spbstu.stackattack.game.data.storeitem.StaticStoreItems;
import com.spbstu.stackattack.game.data.storeitem.StoreItem;
import com.spbstu.stackattack.game.support.SAOptions;
import com.spbstu.stackattack.utils.DoubleUtils;

import java.util.*;

/**
 * Stack attack game data representation class.
 *
 * @author bsi
 */
public class StackAttackData {
    ConversionRequestsContainer conversionRequestsContainer = new ConversionRequestsContainer();

    private final Player player = new Player(conversionRequestsContainer, 0, 0);

    private final StaticStoreItems staticStoreItems = new StaticStoreItems();
    private final FallingStoreItems fallingStoreItems = new FallingStoreItems(player, conversionRequestsContainer);
    private final MovingStoreItems movingStoreItems = new MovingStoreItems(player, conversionRequestsContainer);

    private Random r = new Random();
    private double t = 0.0;

    public StackAttackData() {
        conversionRequestsContainer.set(staticStoreItems, fallingStoreItems, movingStoreItems);

        player.set(staticStoreItems, fallingStoreItems, movingStoreItems);

        fallingStoreItems.set(staticStoreItems, movingStoreItems);
        movingStoreItems.set(staticStoreItems, fallingStoreItems);
    }

    public Iterable<StoreItem> getFallingStoreItems() {
        return fallingStoreItems;
    }

    public Iterable<StoreItem> getStaticStoreItems() {
        return staticStoreItems;
    }

    public Iterable<StoreItem> getMovingStoreItems() {
        return movingStoreItems;
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
        try {
            t += dt;

            if (t > SAOptions.CRANE_OBJECT_GENERATION_FREQUENCY) {
                t -= SAOptions.CRANE_OBJECT_GENERATION_FREQUENCY;

                StoreItem si = new StoreItem(StoreItem.Type.getType(3),
                        r.nextInt(SAOptions.FIELD_WIDTH), SAOptions.FIELD_HEIGHT - 1);

                si.vy = -SAOptions.FALLING_STORE_ITEM_SPEED;

                fallingStoreItems.add(si);
            }

            player.update(dt);
            fallingStoreItems.update(dt);
            movingStoreItems.update(dt);

            conversionRequestsContainer.perform();

            for (int x = 0; x < SAOptions.FIELD_WIDTH; x++) {
                if (staticStoreItems.height(x) == SAOptions.FIELD_HEIGHT) {
                    throw new GameOverEvent();
                }
            }
        } catch (GameOverEvent e) {
            gameOverAction();
        }
    }

    /**
     * Order player to move left.
     */
    public void moveLeft() {
        player.moveLeft();
    }

    /**
     * Order player to move right.
     */
    public void moveRight() {
        player.moveRight();
    }

    /**
     * Order player to jump.
     */
    public void jump() {
        player.jump();
    }

    private void gameOverAction() {
        fallingStoreItems.clear();
        staticStoreItems.clear();
        movingStoreItems.clear();
    }
}
