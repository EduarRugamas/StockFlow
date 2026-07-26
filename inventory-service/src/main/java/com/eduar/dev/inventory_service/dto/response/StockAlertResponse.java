package com.eduar.dev.inventory_service.dto.response;

import com.eduar.dev.inventory_service.wrapper.enums.AlertSeverity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "StockAlertResponse",
    description = "Alerta persistente de stock bajo o crítico"
)
public record StockAlertResponse(

    @Schema(
        description = "Identificador de la alerta",
        example = "5"
    )
    Long id,

    @Schema(
        description = "Identificador del producto",
        example = "1"
    )
    Long productId,

    @Schema(
        description = "Nombre del producto",
        example = "Teclado mecánico"
    )
    String productName,

    @Schema(
        description = "Stock al momento de generar la alerta",
        example = "3"
    )
    Integer currentStock,

    @Schema(
        description = "Stock mínimo configurado",
        example = "5"
    )
    Integer minStock,

    @Schema(
        description = "Nivel de severidad",
        example = "LOW",
        allowableValues = {"LOW", "CRITICAL"}
    )
    AlertSeverity severity
) {}
