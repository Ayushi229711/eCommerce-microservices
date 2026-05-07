package com.ayushi.product_service.Service;

import com.ayushi.product_service.Entity.Product;
import com.ayushi.product_service.Exception.ProductNotFoundException;
import com.ayushi.product_service.Repository.ProductRepository;
import com.ayushi.product_service.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product addProduct(ProductRequest request) {
        Product product = new Product(
                null,
                request.getName(),
                request.getPrice(),
                request.getStock(),
                request.getCategory()
        );

        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Product not found: " + id);
        }

        repository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));
    }
}
