package com.eduar.dev.inventory_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Respuesta de la consulta de alertas de stock")
public record StockAlertListResponse(
        
        @Schema(description = "Lista de alertas registradas")
        List<StockAlertResponse> alerts,

        @Schema(
                description = "Mensaje descriptivo de la operación",
                example = "Alertas consultadas correctamente"
        )
        String message,

        @Schema(
                description = "Indica si la respuesta proviene del fallback",
                example = "false"
        )
        boolean fallback

) {}
