package com.eduar.dev.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduar.dev.inventory_service.dto.request.RegisterMovementRequest;
import com.eduar.dev.inventory_service.dto.response.MovementResponse;
import com.eduar.dev.inventory_service.dto.response.RegisterMovementReponse;
import com.eduar.dev.inventory_service.service.MovementService;
import com.eduar.dev.inventory_service.wrapper.exceptions.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(path = "/api/v1/movements")
@Tag(
        name = "Movements",
        description = "Registro y consulta de movimientos de inventario"
)
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @Operation(
        summary = "Registrar movimiento de inventario",
        description = """
                Registra una entrada IN o una salida OUT.

                La operación actualiza el stock del producto y puede generar
                una alerta persistente cuando el stock cae debajo del mínimo.
            """
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Movimiento registrado correctamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = RegisterMovementReponse.class
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Datos del movimiento inválidos",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ErrorResponse.class
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Producto no encontrado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ErrorResponse.class
                        )
                )
        ),
        @ApiResponse(
                responseCode = "422",
                description = "Stock insuficiente para efectuar la salida",
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
    @PostMapping(path = "")
    public ResponseEntity<RegisterMovementReponse> registerMovement(@Valid @RequestBody RegisterMovementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(movementService.registerMovement(request));
    }


    @Operation(
        summary = "Consultar historial de movimientos",
        description = """
                Obtiene el historial paginado de movimientos pertenecientes
                a un producto, ordenado del más reciente al más antiguo.
                """
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Historial consultado correctamente"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Producto no encontrado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ErrorResponse.class
                        )
                )
        ),
        @ApiResponse(
                responseCode = "429",
                description = "Límite de solicitudes excedido",
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
    @GetMapping(path = "/{productId}/history")
    public ResponseEntity<Page<MovementResponse>> findHistoryByProductId(
        @Parameter(
                description = "Identificador del producto",
                example = "1",
                required = true
        )
        @PathVariable Long productId, 
        @PageableDefault(
                    size = 10,
                    sort = "timestamp",
                    direction = Sort.Direction.DESC
        )
        Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(movementService.findHistoryByProductId(productId, pageable));
    }
    
    

}
