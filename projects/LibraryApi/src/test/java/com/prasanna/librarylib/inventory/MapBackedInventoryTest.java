package com.prasanna.librarylib.inventory;

import com.prasanna.librarylib.domain.InventoryItem;
import com.prasanna.librarylib.makers.InventoryItemMaker;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.natpryce.makeiteasy.MakeItEasy.an;
import static com.natpryce.makeiteasy.MakeItEasy.make;

public class MapBackedInventoryTest {

    private MapBackedInventory mapBackedInventory;
    private JUnitRuleMockery jUnitRuleMockery = new JUnitRuleMockery();
    Map<InventoryItem, Integer> mockMap = jUnitRuleMockery.mock(Map.class);

    @Before
    public void setUp() throws Exception {

        mapBackedInventory = new MapBackedInventory(mockMap);
    }

    @Test
    public void whenAddedNewItemShouldAddItToStock() throws Exception {

        final InventoryItem inventoryItem = make(an(InventoryItemMaker.INVENTORY_ITEM));

        jUnitRuleMockery.checking(new Expectations() {
            {

                allowing(mockMap).containsKey(inventoryItem);
                will(returnValue(false));
                oneOf(mockMap).put(inventoryItem, 1);
            }
        });

        mapBackedInventory.addInventoryItem(inventoryItem);
        jUnitRuleMockery.assertIsSatisfied();
    }

    @Test
    public void whenAddedExistingItemShouldIncreaseTheStock() throws Exception {

        final InventoryItem inventoryItem = make(an(InventoryItemMaker.INVENTORY_ITEM));
        jUnitRuleMockery.checking(new Expectations() {
            {

                allowing(mockMap).containsKey(inventoryItem);
                will(returnValue(true));
                allowing(mockMap).get(inventoryItem);
                will(returnValue(1));
                oneOf(mockMap).put(inventoryItem, 2);
            }
        });

        mapBackedInventory.addInventoryItem(inventoryItem);
        jUnitRuleMockery.assertIsSatisfied();

    }
}