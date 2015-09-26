package com.prasanna.librarylib.domain;

/**
 * Immutable class to represent InventoryItem Stock
 */
public class InventoryItemStock {

    private InventoryItem inventoryItem;
    private int itemCount;

    private InventoryItemStock(InventoryItem inventoryItem, int count) {

        this.inventoryItem = inventoryItem;
        this.itemCount = count;
    }

    public InventoryItem getInventoryItem() {

        return inventoryItem;
    }

    public int getCount() {

        return itemCount;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryItemStock that = (InventoryItemStock) o;

        if (itemCount != that.itemCount) return false;
        if (inventoryItem != null ? !inventoryItem.equals(that.inventoryItem) : that.inventoryItem != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = inventoryItem != null ? inventoryItem.hashCode() : 0;
        result = 31 * result + itemCount;
        return result;
    }

    public static InventoryItemStock of(InventoryItem inventoryItem, int itemCount) {

        return new InventoryItemStock(inventoryItem, itemCount);
    }
}
