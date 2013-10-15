package com.spbstu.stackattack.game.data;

import com.spbstu.stackattack.game.data.storeitem.FallingStoreItems;
import com.spbstu.stackattack.game.data.storeitem.MovingStoreItems;
import com.spbstu.stackattack.game.data.storeitem.StaticStoreItems;
import com.spbstu.stackattack.game.data.storeitem.StoreItem;

import java.util.ArrayList;

public class ConversionRequestsContainer {
    public static class ConversionRequest {
        public enum ConversionType {
            STATIC_TO_FALLING, STATIC_TO_MOVING, FALLING_TO_STATIC, FALLING_TO_MOVING, MOVING_TO_STATIC, MOVING_TO_FALLING;
        }

        private final StoreItem storeItem;
        private final ConversionType conversionType;

        public ConversionRequest(final StoreItem storeItem, final ConversionType conversionType) {
            this.storeItem = storeItem;
            this.conversionType = conversionType;
        }

        private StoreItem getStoreItem() {
            return storeItem;
        }

        private ConversionType getConversionType() {
            return conversionType;
        }
    }

    private ArrayList<ConversionRequest> conversionRequests = new ArrayList<ConversionRequest>();

    private StaticStoreItems staticStoreItems = null;
    private FallingStoreItems fallingStoreItems = null;
    private MovingStoreItems movingStoreItems = null;

    public void set(final StaticStoreItems staticStoreItems, final FallingStoreItems fallingStoreItems, final MovingStoreItems movingStoreItems) {
        this.staticStoreItems = staticStoreItems;
        this.fallingStoreItems = fallingStoreItems;
        this.movingStoreItems = movingStoreItems;
    }

    public void add(final ConversionRequest conversionRequest) {
        conversionRequests.add(conversionRequest);
    }

    public void perform() {
        for (final ConversionRequest conversionRequest : conversionRequests) {
            final StoreItem si = conversionRequest.getStoreItem();

            switch (conversionRequest.conversionType) {
                case STATIC_TO_FALLING:
                    staticStoreItems.remove(si);
                    fallingStoreItems.add(si);
                    break;
                case STATIC_TO_MOVING:
                    staticStoreItems.remove(si);
                    movingStoreItems.add(si);
                    break;
                case FALLING_TO_STATIC:
                    fallingStoreItems.remove(si);
                    staticStoreItems.add(si);
                    break;
                case FALLING_TO_MOVING:
                    fallingStoreItems.remove(si);
                    movingStoreItems.add(si);
                    break;
                case MOVING_TO_STATIC:
                    movingStoreItems.remove(si);
                    staticStoreItems.add(si);
                    break;
                case MOVING_TO_FALLING:
                    movingStoreItems.remove(si);
                    fallingStoreItems.add(si);
                    break;
            }
        }

        conversionRequests.clear();
    }
}
