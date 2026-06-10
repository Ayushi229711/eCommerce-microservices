package com.ayushi.inventory_service.Service;


import com.ayushi.inventory_service.Entity.Inventory;
import com.ayushi.inventory_service.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public boolean isInStock(Long productId, Integer quantity) {

        Inventory inventory =
                inventoryRepository.findById(productId)
                        .orElse(null);

        return inventory != null &&
                inventory.getQuantity() >= quantity;
    }

    public void reduceStock(Long productId, Integer quantity) {

        Inventory inventory =
                inventoryRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException("Product not found"));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setQuantity(
                inventory.getQuantity() - quantity
        );

        inventoryRepository.save(inventory);
    }
}
