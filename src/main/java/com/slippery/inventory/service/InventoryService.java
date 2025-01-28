package com.slippery.inventory.service;

import com.slippery.inventory.dto.InventoryDto;
import com.slippery.inventory.models.Inventory;



public interface InventoryService {
    InventoryDto createNewInventory(Inventory inventory);
    InventoryDto updateInventory(Inventory inventory,Long id);
    InventoryDto findById(Long id);
    InventoryDto deleteById(Long id);
    InventoryDto isInStock(String skuCode,Long quantity);
}
