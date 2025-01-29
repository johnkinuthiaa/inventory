package com.slippery.inventory.service.impl;

import com.slippery.inventory.dto.InventoryDto;
import com.slippery.inventory.models.Inventory;
import com.slippery.inventory.repository.InventoryRepository;
import com.slippery.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
public class InventoryServiceImplementation implements InventoryService {
    private final InventoryRepository repository;

    public InventoryServiceImplementation(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public InventoryDto createNewInventory(Inventory inventory) {
        InventoryDto response =new InventoryDto();
        repository.save(inventory);
        log.info("inventory created!");
        response.setMessage("inventory created");
        response.setInventory(inventory);
        response.setStatusCode(200);

        return response;
    }

    @Override
    public InventoryDto updateInventory(Inventory inventory, Long id) {
        InventoryDto response =new InventoryDto();
        Optional<Inventory> existingInventory =repository.findById(id);

        return null;
    }

    @Override
    public InventoryDto findById(Long id) {
        InventoryDto response =new InventoryDto();
        Optional<Inventory> existingInventory =repository.findById(id);
        if(existingInventory.isEmpty()){
            response.setMessage("inventory not found! ");
            log.info("inventory not found");
            response.setStatusCode(404);
            return response;
        }
        response.setMessage("inventory with id "+id);
        response.setStatusCode(200);
        response.setInventory(existingInventory.get());
        return response;
    }

    @Override
    public InventoryDto deleteById(Long id) {
        InventoryDto response =new InventoryDto();
        Optional<Inventory> existingInventory =repository.findById(id);
        if(existingInventory.isEmpty()){
            response.setMessage("inventory not found! ");
            log.info("inventory not found");
            response.setStatusCode(404);
            return response;
        }
        repository.deleteById(id);
        response.setMessage("Inventory deleted");
        log.info("inventory deleted");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public InventoryDto isInStock(String skuCode, Long quantity) {
        InventoryDto response =new InventoryDto();
        Optional<List<Inventory>> existingInventory =Optional.of(repository.findAll().stream()
                .filter(inventory -> inventory.getSkuCode().equalsIgnoreCase(skuCode))
                .toList());
        if(existingInventory.get().isEmpty()){
            response.setMessage("item not found in inventory");
            response.setStatusCode(404);
            return response;
        }
        if(existingInventory.get().get(0).getQuantity()<quantity){
            response.setMessage("item is not in stock. Quantity remaining: "+existingInventory.get().get(0).getQuantity());
            response.setInStock(false);
            response.setStatusCode(204);
            return response;
        }
        response.setMessage("item is in stock. Quantity remaining: "+existingInventory.get().get(0).getQuantity());
        response.setInStock(true);
        response.setStatusCode(200);

        return response;
    }

    @Override
    public InventoryDto subtractOrderedItems(String skuCode, Long quantity) {
        InventoryDto response =new InventoryDto();
        InventoryDto inStock =isInStock(skuCode,quantity);
        Optional<List<Inventory>> existingInventory =Optional.of(repository.findAll().stream()
                .filter(inventory -> inventory.getSkuCode().equalsIgnoreCase(skuCode))
                .toList());
        log.info("in stock :{}", inStock.isInStock());
        if(!inStock.isInStock()){
            response.setInStock(false);
            response.setMessage("Item is not in stock!");
            response.setStatusCode(204);
            return response;
        }
        long balance =existingInventory.get().get(0).getQuantity()-quantity;
        existingInventory.get().get(0).setQuantity(balance);
        repository.save(existingInventory.get().get(0));
        response.setMessage("Removed "+quantity+" items from "+skuCode+"'s from inventory" +
                " Quantity remaining : "+balance);
        response.setStatusCode(200);
        response.setInStock(balance > 0);
        return response;
    }
}
