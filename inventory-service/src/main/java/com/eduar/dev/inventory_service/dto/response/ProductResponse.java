package com.eduar.dev.inventory_service.dto.response;

import java.math.BigDecimal;

import com.eduar.dev.inventory_service.wrapper.enums.ProductCategory;

public record ProductResponse(
    Long id,
    String sku,
    String name,
    ProductCategory category,
    Integer currentStock,
    Integer minStock,
    BigDecimal unitPrice
) {}
