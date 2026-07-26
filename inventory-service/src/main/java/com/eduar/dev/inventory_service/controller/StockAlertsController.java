package com.eduar.dev.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduar.dev.inventory_service.dto.response.StockAlertResponse;
import com.eduar.dev.inventory_service.service.StockAlertService;
import com.eduar.dev.inventory_service.wrapper.exceptions.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping(path = "/api/v1/alerts")
@Tag(
    name = "Stock Alerts",
    description = "Consulta de alertas persistentes de stock"
)
public class StockAlertsController {

    private final StockAlertService stockAlertService;

    public StockAlertsController(StockAlertService stockAlertService) {
        this.stockAlertService = stockAlertService;
    }

    @Operation(
        summary = "Consultar alertas de stock",
        description = """
                Devuelve las alertas persistentes generadas cuando el stock
                de un producto cae por debajo del mínimo configurado.
                """
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Alertas consultadas correctamente"
        ),
        @ApiResponse(
                responseCode = "503",
                description = "El servicio de alertas no se encuentra disponible",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ErrorResponse.class
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ErrorResponse.class
                        )
                )
        )
    })
    @GetMapping(path = "")
    public ResponseEntity<Page<StockAlertResponse>> getAllStockAlerts(
         @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC
        )
        Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(stockAlertService.getAllStockAlerts(pageable));
    }
    

}
