package com.spbstu.stackattack.game.data;

import com.spbstu.stackattack.game.support.StackAttackOptions;
import com.spbstu.stackattack.utils.DoubleUtils;

import java.util.ArrayList;

public class StaticStoreItems extends ArrayList<StoreItem> {
    private int [] heightMap = new int [StackAttackOptions.FIELD_WIDTH];

    public StaticStoreItems() {
        super(StackAttackOptions.FIELD_WIDTH * StackAttackOptions.FIELD_HEIGHT);

        for (int heightMapIndex = 0; heightMapIndex < heightMap.length; heightMapIndex++) {
            heightMap[heightMapIndex] = 0;
        }
    }

    public int height(final int x) {
        return heightMap[x];
    }

    public StoreItem getAndRemoveFromTop(final int x) {
        int storeItemIndex = -1;
        StoreItem storeItem = null;

        for (int siIndex = 0; siIndex < this.size(); siIndex++) {
            StoreItem si = this.get(siIndex);

            if (DoubleUtils.equals(si.x, x)) {
                if (storeItem == null || si.y > storeItem.y) {
                    storeItem = si;
                    storeItemIndex = siIndex;
                }
            }
        }

        if (storeItem != null) {
            remove(storeItemIndex);
        }

        return storeItem;
    }

    @Override
    public boolean add(final StoreItem storeItem) {
        heightMap[(int)storeItem.x] = (int)storeItem.y + 1;

        synchronized (this) {
            return super.add(storeItem);
        }
    }

    @Override
    public StoreItem remove(final int index) {
        StoreItem storeItem;

        synchronized (this) {
            storeItem = super.remove(index);
        }

        heightMap[(int)storeItem.x] = Math.max((int)storeItem.y, 0);

        return storeItem;
    }

    @Override
    public void clear() {
        synchronized (this) {
            super.clear();
        }

        for (int heightMapIndex = 0; heightMapIndex < heightMap.length; heightMapIndex++) {
            heightMap[heightMapIndex] = 0;
        }
    }
}
