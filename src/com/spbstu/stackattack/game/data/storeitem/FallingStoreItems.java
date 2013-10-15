package com.spbstu.stackattack.game.data.storeitem;

import com.spbstu.stackattack.game.data.ConversionRequestsContainer;
import com.spbstu.stackattack.game.data.Player;
import com.spbstu.stackattack.game.data.event.GameOverEvent;
import com.spbstu.stackattack.game.support.SAOptions;
import com.spbstu.stackattack.utils.DoubleUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FallingStoreItems implements Iterable<StoreItem> {
    private List<StoreItem> items = new ArrayList<StoreItem>();

    private final Player player;

    private final ConversionRequestsContainer conversionRequestsContainer;

    private StaticStoreItems staticStoreItems;
    private MovingStoreItems movingStoreItems;

    public FallingStoreItems(final Player player, final ConversionRequestsContainer conversionRequestsContainer) {
        this.player = player;
        this.conversionRequestsContainer = conversionRequestsContainer;
    }

    public void set(final StaticStoreItems staticStoreItems, final MovingStoreItems movingStoreItems) {
        this.staticStoreItems = staticStoreItems;
        this.movingStoreItems = movingStoreItems;
    }

    public synchronized void add(final StoreItem item) {
        items.add(item);
    }

    public Iterator<StoreItem> iterator() {
        return items.iterator();
    }

    public synchronized void remove(final StoreItem item) {
        items.remove(item);
    }

    public synchronized void clear() {
        items.clear();
    }

    public void update(final double dt) throws GameOverEvent {
        for (final StoreItem item : items) {
            item.vy = -SAOptions.FALLING_STORE_ITEM_SPEED;

            if (playerHit(item)) {
                throw new GameOverEvent();
            }

            if (onStaticItemsHit(item, dt)) {
                return;
            }

            item.y += item.vy * dt;
        }
    }

    private boolean playerHit(final StoreItem item) {
        return (DoubleUtils.less(player.x - 1, item.x) && DoubleUtils.less(item.x, player.x + 1)) &&
                DoubleUtils.less(player.y - 1, item.y) && DoubleUtils.less(item.y, player.y + 2);
    }

    private boolean onStaticItemsHit(final StoreItem item, final double dt) {
        double newY = item.y + item.vy * dt;

        int h = staticStoreItems.height((int)item.x);

        if (newY < h) {
            item.y = h;

            conversionRequestsContainer.add(new ConversionRequestsContainer.ConversionRequest(item, ConversionRequestsContainer.ConversionRequest.ConversionType.FALLING_TO_STATIC));
            return true;
        }

        return false;
    }
}
