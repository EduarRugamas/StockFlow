package com.eduar.dev.inventory_service.dto.request;

import com.eduar.dev.inventory_service.wrapper.enums.MovementType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(
    name = "RegisterMovementRequest",
    description = "Datos requeridos para registrar un movimiento de inventario"
)
public record RegisterMovementRequest(

    @Schema(
        description = "Identificador del producto",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El id del producto es obligatorio")
    Long productId,

    @Schema(
        description = "Tipo de movimiento",
        example = "OUT",
        allowableValues = {"IN", "OUT"},
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El tipo de movimiento es obligatorio")
    MovementType type,

    @Schema(
        description = "Cantidad de unidades del movimiento",
        example = "3",
        minimum = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que cero")
    Integer quantity,

    @Schema(
        description = "Motivo del movimiento",
        example = "Venta de producto",
        maxLength = 255,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 255, message = "El motivo no puede superar los 255 caracteres")
    String reason
    
) {}
