package com.prasanna.librarylib.makers;

import com.natpryce.makeiteasy.Instantiator;

import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import com.prasanna.librarylib.domain.InventoryItem;
import com.prasanna.librarylib.domain.InventoryItemType;

public class InventoryItemMaker {

    private static final Property<? super InventoryItem, Integer> ITEM_ID = Property.newProperty();
    private static final Property<? super InventoryItem, String> ITEM_DESCRIPTION = Property.newProperty();
    private static final Property<? super InventoryItem, InventoryItemType> ITEM_TYPE = Property.newProperty();
    public static final Instantiator<InventoryItem> INVENTORY_ITEM = new Instantiator<InventoryItem>() {
        @Override
        public InventoryItem instantiate(PropertyLookup<InventoryItem> propertyLookup) {

            InventoryItem inventoryItem = new InventoryItem(
                    propertyLookup.valueOf(ITEM_ID, 1),
                    propertyLookup.valueOf(ITEM_DESCRIPTION, "Java Unleashed"),
                    propertyLookup.valueOf(ITEM_TYPE, InventoryItemType.BOOK)

            );

            return inventoryItem;
        }
    };

}
