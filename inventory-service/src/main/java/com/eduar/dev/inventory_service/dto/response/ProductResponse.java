package com.eduar.dev.inventory_service.dto.response;

import java.math.BigDecimal;

import com.eduar.dev.inventory_service.wrapper.enums.ProductCategory;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ProductResponse",
    description = "Información completa de un producto"
)
public record ProductResponse(
    
    @Schema(example = "1")
    Long id,

    @Schema(example = "ELEC-001")
    String sku,

    @Schema(example = "Teclado mecánico")
    String name,

    @Schema(example = "ELECTRONICS")
    ProductCategory category,

    @Schema(example = "20")
    Integer currentStock,

    @Schema(example = "5")
    Integer minStock,

    @Schema(example = "45.99")
    BigDecimal unitPrice
) {}
