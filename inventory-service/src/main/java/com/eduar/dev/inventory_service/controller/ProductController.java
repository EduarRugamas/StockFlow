package com.eduar.dev.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduar.dev.inventory_service.dto.request.CreateProductRequest;
import com.eduar.dev.inventory_service.dto.response.ProductResponse;
import com.eduar.dev.inventory_service.service.ProductService;
import com.eduar.dev.inventory_service.wrapper.enums.ProductCategory;

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
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping(path = "")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(productService.CreateProduct(request));
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductResponse>> ListProduct(@RequestParam(required = false) ProductCategory category, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(productService.findAll(category, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> ProductById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(productService.findByProductId(id));
    }
    
    
    
    

}
