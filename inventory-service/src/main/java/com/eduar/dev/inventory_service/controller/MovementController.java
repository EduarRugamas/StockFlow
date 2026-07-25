package com.eduar.dev.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduar.dev.inventory_service.dto.request.RegisterMovementRequest;
import com.eduar.dev.inventory_service.dto.response.MovementResponse;
import com.eduar.dev.inventory_service.dto.response.RegisterMovementReponse;
import com.eduar.dev.inventory_service.service.MovementService;

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
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping(path = "")
    public ResponseEntity<RegisterMovementReponse> registerMovement(@Valid @RequestBody RegisterMovementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(movementService.registerMovement(request));
    }

    @GetMapping(path = "/{productId}/history")
    public ResponseEntity<Page<MovementResponse>> findHistoryByProductId(@PathVariable Long productId, 
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
