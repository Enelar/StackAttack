package com.spbstu.stackattack.game.data.storeitem;

import com.spbstu.stackattack.game.data.ConversionRequestsContainer;
import com.spbstu.stackattack.game.data.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MovingStoreItems implements Iterable<StoreItem> {
    private List<StoreItem> items = new ArrayList<StoreItem>();

    private final Player player;

    private final ConversionRequestsContainer conversionRequestsContainer;

    private StaticStoreItems staticStoreItems;
    private FallingStoreItems fallingStoreItems;

    public MovingStoreItems(final Player player, final ConversionRequestsContainer conversionRequestsContainer) {
        this.player = player;
        this.conversionRequestsContainer = conversionRequestsContainer;
    }

    public void set(final StaticStoreItems staticStoreItems, final FallingStoreItems fallingStoreItems) {
        this.staticStoreItems = staticStoreItems;
        this.fallingStoreItems = fallingStoreItems;
    }

    public void update(final double dt) {
        for (StoreItem item : items) {
            /* Update code */
        }
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
}
