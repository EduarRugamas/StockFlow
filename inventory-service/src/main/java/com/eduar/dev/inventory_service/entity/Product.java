package com.eduar.dev.inventory_service.entity;

import java.math.BigDecimal;

import com.eduar.dev.inventory_service.wrapper.enums.ProductCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mnt_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;
    
    @Column(nullable = false)
    private Integer currentStock;

    @Column(nullable = false)
    private Integer minStock;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;


    public void increaseStock(Integer quantity) {
        validateQuantity(quantity);
        this.currentStock += quantity;
    }

    public void decreaseStock(Integer quantity) {
        validateQuantity(quantity);

        if (this.currentStock < quantity) {
            // throw new InsufficientStockException(
            //         "Stock insuficiente. Disponible: " + currentStock
            //                 + ", solicitado: " + quantity
            // );
        }

        this.currentStock -= quantity;
    }

    public boolean isBelowMinimumStock() {
        return this.currentStock < this.minStock;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero"
            );
        }
    }
}
