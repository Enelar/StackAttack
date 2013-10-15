package com.spbstu.stackattack.game.data.storeitem;

import com.spbstu.stackattack.game.support.SAOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaticStoreItems implements Iterable<StoreItem> {
    private List<StoreItem> items = new ArrayList<StoreItem>();

    private int [] heightMap = new int [SAOptions.FIELD_WIDTH];

    public StaticStoreItems() {
        clear();
    }

    public int height(final int x) {
        return heightMap[x];
    }

    public synchronized void add(final StoreItem item) {
        heightMap[(int)item.x] = (int)item.y + 1;

        items.add(item);
    }

    public synchronized void remove(final StoreItem item) {
        if (height((int)item.x) == (int)item.y + 1) {
            heightMap[(int)item.x] = (int)item.y;
        }

        items.remove(item);
    }

    public Iterator<StoreItem> iterator() {
        return items.iterator();
    }

    public synchronized void clear() {
        items.clear();

        for (int x = 0; x < SAOptions.FIELD_WIDTH; x++) {
            heightMap[x] = 0;
        }
    }
}
