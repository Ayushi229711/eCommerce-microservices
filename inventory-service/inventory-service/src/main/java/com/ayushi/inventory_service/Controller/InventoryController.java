package com.ayushi.inventory_service.Controller;


import com.ayushi.inventory_service.Entity.Inventory;
import com.ayushi.inventory_service.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public Inventory addInventory(
            @RequestBody Inventory inventory
    ) {
        return inventoryService.addInventory(inventory);
    }

    @GetMapping("/{productId}/{quantity}")
    public boolean isInStock(
            @PathVariable Long productId,
            @PathVariable Integer quantity
    ) {
        return inventoryService.isInStock(productId, quantity);
    }

    @PutMapping("/{productId}/{quantity}")
    public String reduceStock(
            @PathVariable Long productId,
            @PathVariable Integer quantity
    ) {

        inventoryService.reduceStock(productId, quantity);

        return "Stock Updated";
    }
}
