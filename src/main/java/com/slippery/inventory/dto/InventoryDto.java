package com.slippery.inventory.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.inventory.models.Inventory;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryDto {
    private String message;
    private int statusCode;
    private Inventory inventory;
    private boolean isInStock;
    private List<Inventory> inventoryList;
}
