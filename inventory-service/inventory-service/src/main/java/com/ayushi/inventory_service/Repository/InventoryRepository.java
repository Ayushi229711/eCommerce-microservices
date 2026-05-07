package com.ayushi.inventory_service.Repository;

import com.ayushi.inventory_service.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
