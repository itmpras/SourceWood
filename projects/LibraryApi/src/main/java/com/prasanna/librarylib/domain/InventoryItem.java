package com.prasanna.librarylib.domain;

/**
 * Immutable class to represent an Inventory item
 */
public class InventoryItem {
    private int itemId;
    private String itemDescription;
    private InventoryItemType inventoryItemType;

    public InventoryItem(int itemId, String itemDescription, InventoryItemType inventoryItemType) {

        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.inventoryItemType = inventoryItemType;
    }

    public int getItemId() {

        return itemId;
    }

    public String getItemDescription() {

        return itemDescription;
    }

    public InventoryItemType getInventoryItemType() {

        return inventoryItemType;
    }

    @Override
    public String toString() {

        return "InventoryItem{" +
                "itemId=" + itemId +
                ", itemDescription='" + itemDescription + '\'' +
                ", inventoryItemType=" + inventoryItemType +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryItem that = (InventoryItem) o;

        if (itemId != that.itemId) return false;
        if (inventoryItemType != that.inventoryItemType) return false;
        if (!itemDescription.equals(that.itemDescription)) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = itemId;
        result = 31 * result + itemDescription.hashCode();
        result = 31 * result + inventoryItemType.hashCode();
        return result;
    }
}
