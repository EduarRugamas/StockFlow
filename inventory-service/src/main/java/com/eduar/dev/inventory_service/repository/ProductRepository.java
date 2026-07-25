package com.eduar.dev.inventory_service.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eduar.dev.inventory_service.entity.Product;
import com.eduar.dev.inventory_service.wrapper.enums.ProductCategory;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySkuIgnoreCase(String sku);

    Page<Product> findByCategory(ProductCategory category, Pageable page);

    @Query("""
            SELECT product
            FROM Product product
            WHERE product.currentStock <= product.minStock
            ORDER BY product.currentStock ASC
            """)
    List<Product> findProductsWithLowStock();

}
