package com.prasanna.librarylib.inventory;

import com.prasanna.librarylib.domain.InventoryItem;
import com.prasanna.librarylib.domain.InventoryItemStock;

import java.util.List;

/**
 * Created by gopinithya on 26/09/15.
 */
public interface Inventory {
    void addInventoryItem(InventoryItem inventoryItem);

    List<InventoryItemStock> listInventoryItems();
}
