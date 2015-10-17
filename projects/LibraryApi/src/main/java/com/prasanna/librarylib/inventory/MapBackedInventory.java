package com.prasanna.librarylib.inventory;

import com.prasanna.librarylib.domain.InventoryItem;
import com.prasanna.librarylib.domain.InventoryItemStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by gopinithya on 26/09/15.
 */
public class MapBackedInventory implements Inventory {

    private static Logger log = LoggerFactory.getLogger(MapBackedInventory.class);
    Map<InventoryItem, Integer> inventoryItemMap;

    public MapBackedInventory(Map<InventoryItem, Integer> inventoryItemMap) {

        this.inventoryItemMap = inventoryItemMap;
    }

    @Override

    public void addInventoryItem(InventoryItem inventoryItem) {

        log.debug("Adding Item to Inventory {} ", inventoryItem);
        // TODO TO Refactor to avoid duplication
        if (inventoryItemMap.containsKey(inventoryItem)) {
            log.debug("Increasing Stock");
            Integer integer = inventoryItemMap.get(inventoryItem);
            inventoryItemMap.put(inventoryItem, integer + 1);

        } else {
            log.debug("New stock for the Item {}", inventoryItem);
            inventoryItemMap.put(inventoryItem, 1);
        }
    }

    @Override
    public List<InventoryItemStock> listInventoryItems() {

        return null;
    }
}
