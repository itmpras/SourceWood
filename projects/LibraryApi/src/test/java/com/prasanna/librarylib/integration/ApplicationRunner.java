package com.prasanna.librarylib.integration;

import com.prasanna.librarylib.inventory.Inventory;
import com.prasanna.librarylib.domain.InventoryItem;
import com.prasanna.librarylib.domain.InventoryItemStock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gopinithya on 26/09/15.
 */
public class ApplicationRunner {

    private Inventory inventory;

    public ApplicationRunner(Inventory inventory) {

        this.inventory = inventory;
    }

    public void whenInventoryHasItem(InventoryItem inventoryItem) {

        inventory.addInventoryItem(inventoryItem);
    }

    public void shouldListItemStock(InventoryItemStock inventoryItemStock) {

        List<InventoryItemStock> actualStock = new ArrayList<InventoryItemStock>();
        actualStock.add(inventoryItemStock);

        List<InventoryItemStock> expectedStock = inventory.listInventoryItems();
        assertThat(actualStock, hasItems(expectedStock.toArray(new InventoryItemStock[expectedStock.size()])));
    }
}
