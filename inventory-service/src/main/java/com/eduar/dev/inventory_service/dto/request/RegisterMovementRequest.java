package com.eduar.dev.inventory_service.dto.request;

import com.eduar.dev.inventory_service.wrapper.enums.MovementType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RegisterMovementRequest(

    @NotNull(message = "El id del producto es obligatorio")
    Long productId,

    @NotNull(message = "El tipo de movimiento es obligatorio")
    MovementType type,

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que cero")
    Integer quantity,

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 255, message = "El motivo no puede superar los 255 caracteres")
    String reason
    
) {}
