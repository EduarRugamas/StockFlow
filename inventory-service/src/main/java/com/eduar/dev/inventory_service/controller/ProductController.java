package com.eduar.dev.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduar.dev.inventory_service.dto.request.CreateProductRequest;
import com.eduar.dev.inventory_service.dto.response.ProductResponse;
import com.eduar.dev.inventory_service.service.ProductService;
import com.eduar.dev.inventory_service.wrapper.enums.ProductCategory;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(path = "/api/v1/products")
@Tag(
    name = "Products",
    description = "Consulta y administración de productos"
)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto en el inventario"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Producto creado correctamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ProductResponse.class
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Datos del producto inválidos",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ErrorResponse.class
                        )
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Ya existe un producto con el SKU indicado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ErrorResponse.class
                        )
                )
        )
    })
    @PostMapping(path = "")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(productService.CreateProduct(request));
    }

    @Operation(
        summary = "Consultar productos",
        description = """
                Obtiene los productos de forma paginada.
                Opcionalmente permite filtrar por categoría.
                """
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Productos consultados correctamente"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Parámetros de consulta inválidos",
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
    @GetMapping("")
    public ResponseEntity<Page<ProductResponse>> ListProduct(@RequestParam(required = false) ProductCategory category, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(productService.findAll(category, pageable));
    }

    @Operation(
        summary = "Consultar producto por ID",
        description = "Busca un producto utilizando su identificador único"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Producto encontrado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = ProductResponse.class
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
    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> ProductById(
        @Parameter(
                description = "Identificador del producto",
                example = "1",
                required = true
        )
        @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(productService.findByProductId(id));
    }
    
    
    
    

}
