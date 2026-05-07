package com.ayushi.product_service.Repository;

import com.ayushi.product_service.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
