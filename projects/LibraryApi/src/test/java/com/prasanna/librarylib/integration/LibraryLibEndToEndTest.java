package com.prasanna.librarylib.integration;

import com.prasanna.librarylib.inventory.Inventory;
import com.prasanna.librarylib.domain.InventoryItem;
import com.prasanna.librarylib.domain.InventoryItemStock;
import com.prasanna.librarylib.inventory.MapBackedInventory;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

import static com.natpryce.makeiteasy.MakeItEasy.an;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.prasanna.librarylib.makers.InventoryItemMaker.INVENTORY_ITEM;

/**
 * Created by gopinithya on 26/09/15.
 */

public class LibraryLibEndToEndTest {

    private JUnitRuleMockery jUnitRuleMockery = new JUnitRuleMockery();
    private Inventory inventory = new MapBackedInventory();

    private ApplicationRunner applicationRunner = new ApplicationRunner(inventory);

    @Test
    public void inventoryShouldReturnLoanableInventoryItems() throws Exception {

        InventoryItem inventoryItem = make(an(INVENTORY_ITEM));
        applicationRunner.whenInventoryHasItem(inventoryItem);
        applicationRunner.shouldListItemStock(InventoryItemStock.of(inventoryItem, 1));

    }

}
