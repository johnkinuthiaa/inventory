package com.slippery.inventory.controller;

import com.slippery.inventory.dto.InventoryDto;
import com.slippery.inventory.models.Inventory;
import com.slippery.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<InventoryDto> createNewInventory(@RequestBody Inventory inventory){
        return ResponseEntity.ok(service.createNewInventory(inventory));
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<InventoryDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<InventoryDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/check-stock")
    public ResponseEntity<InventoryDto> getInStock(@RequestParam String skuCode,@RequestParam Long quantity) {
        return ResponseEntity.ok(service.isInStock(skuCode, quantity));
    }

    @PutMapping("/update/items")
    public ResponseEntity<InventoryDto> updateInventoryItemsQuantity(@RequestParam String skuCode,@RequestParam Long quantity) {
        return ResponseEntity.ok(service.subtractOrderedItems(skuCode, quantity));
    }
}
