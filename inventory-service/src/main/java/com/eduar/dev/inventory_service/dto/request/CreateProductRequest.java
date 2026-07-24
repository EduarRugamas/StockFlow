package com.eduar.dev.inventory_service.dto.request;

import java.math.BigDecimal;

import com.eduar.dev.inventory_service.wrapper.enums.ProductCategory;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(

    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 50, message = "El SKU no puede superar los 50 caracteres")
    String sku,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    String name,

    @NotNull(message = "La categoría es obligatoria")
    ProductCategory category,

    @NotNull(message = "El stock actual es obligatorio")
    @PositiveOrZero(message = "El stock actual no puede ser negativo")
    Integer currentStock,

    @NotNull(message = "El stock mínimo es obligatorio")
    @PositiveOrZero(message = "El stock mínimo no puede ser negativo")
    Integer minStock,

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser mayor que cero")
    @Digits(
        integer = 10,
        fraction = 2,
        message = "El precio puede tener hasta 10 enteros y 2 decimales"
    )
    BigDecimal unitPrice

) {}
