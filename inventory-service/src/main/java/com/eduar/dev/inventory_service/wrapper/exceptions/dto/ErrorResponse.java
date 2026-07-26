package com.eduar.dev.inventory_service.wrapper.exceptions.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ErrorResponse",
    description = "Respuesta estándar de error de la API"
)
public record ErrorResponse(

    @Schema(
        description = "Código HTTP",
        example = "404"
    )
    Integer status,

    @Schema(
        description = "Nombre del error HTTP",
        example = "NOT_FOUND"
    )
    String error,

    @Schema(
        description = "Descripción del error",
        example = "Producto no encontrado con id: 100"
    )
    String message,

    @Schema(
        description = "Ruta donde ocurrió el error",
        example = "/api/v1/products/100"
    )
    String path,

    @Schema(
        description = "Fecha y hora del error",
        example = "2026-07-25T11:30:00"
    )
    LocalDateTime timestamp
) {}
