package com.eduar.dev.inventory_service.dto.response;

import java.time.LocalDateTime;

import com.eduar.dev.inventory_service.wrapper.enums.MovementType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "RegisterMovementResponse",
    description = "Resultado del registro de un movimiento"
)
public record RegisterMovementReponse(
    
    @Schema(
        description = "Identificador del movimiento",
        example = "15"
    )
    Long id,

    @Schema(
        description = "Identificador del producto",
        example = "1"
    )
    Long productId,
    
    @Schema(
        description = "SKU del producto",
        example = "ELEC-001"
    )
    String productSku,

    @Schema(
        description = "Nombre del producto",
        example = "Teclado mecánico"
    )
    String productName,

    @Schema(
        description = "Tipo del movimiento",
        example = "OUT"
    )
    MovementType type,
    
    @Schema(
        description = "Cantidad registrada",
        example = "3"
    )
    Integer quantity,

    @Schema(
        description = "Motivo del movimiento",
        example = "Venta de producto"
    )
    String reason,

    @Schema(
        description = "Fecha y hora del movimiento",
        example = "2026-07-25T11:30:00"
    )
    LocalDateTime timestamp
) {}
