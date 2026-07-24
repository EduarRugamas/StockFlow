package com.eduar.dev.inventory_service.service;

import org.springframework.stereotype.Service;

import com.eduar.dev.inventory_service.dto.request.RegisterMovementRequest;
import com.eduar.dev.inventory_service.dto.response.RegisterMovementReponse;
import com.eduar.dev.inventory_service.entity.Movement;
import com.eduar.dev.inventory_service.entity.Product;
import com.eduar.dev.inventory_service.repository.MovementRepository;
import com.eduar.dev.inventory_service.repository.ProductRepository;
import com.eduar.dev.inventory_service.wrapper.enums.MovementType;
import com.eduar.dev.inventory_service.wrapper.exceptions.ProductNotFoundException;

@Service
public class MovementService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;


    public MovementService(MovementRepository movementRepository, ProductRepository productRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
    }

    public RegisterMovementReponse registerMovement(RegisterMovementRequest request) {

        Product product = productRepository.findById(request.productId()).orElseThrow(
                    () -> new ProductNotFoundException("Producto no encontrado con id: " + request.productId())
        );

         if (request.type() == MovementType.IN) {
            product.increaseStock(request.quantity());
        } else {
            product.decreaseStock(request.quantity());
        }

        Movement movement = Movement.builder()
                                .product(product)
                                .type(request.type())
                                .quantity(request.quantity())
                                .reason(request.reason())
                                .build();

        switch (request.type()) {
            case IN -> product.increaseStock(request.quantity());
            case OUT -> product.decreaseStock(request.quantity());
        }

        Movement newMovement = movementRepository.save(movement);
        
        return new RegisterMovementReponse(
                    newMovement.getId(), 
                    product.getId(), 
                    product.getSku(), 
                    product.getName(), 
                    newMovement.getType(),
                    newMovement.getQuantity(),
                    newMovement.getReason(), 
                    newMovement.getTimestamp()
        );

    }

    

}
