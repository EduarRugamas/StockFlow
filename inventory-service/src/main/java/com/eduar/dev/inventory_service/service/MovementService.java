package com.eduar.dev.inventory_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduar.dev.inventory_service.dto.request.RegisterMovementRequest;
import com.eduar.dev.inventory_service.dto.response.MovementResponse;
import com.eduar.dev.inventory_service.dto.response.RegisterMovementReponse;
import com.eduar.dev.inventory_service.entity.Movement;
import com.eduar.dev.inventory_service.entity.Product;
import com.eduar.dev.inventory_service.entity.StockAlert;
import com.eduar.dev.inventory_service.repository.MovementRepository;
import com.eduar.dev.inventory_service.repository.ProductRepository;
import com.eduar.dev.inventory_service.repository.StockAlertRepository;
import com.eduar.dev.inventory_service.wrapper.enums.AlertSeverity;
import com.eduar.dev.inventory_service.wrapper.enums.MovementType;
import com.eduar.dev.inventory_service.wrapper.exceptions.ProductNotFoundException;

@Service
@Transactional(readOnly = true)
public class MovementService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final StockAlertRepository stockAlertRepository;


    public MovementService(MovementRepository movementRepository, ProductRepository productRepository, StockAlertRepository stockAlertRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
        this.stockAlertRepository = stockAlertRepository;
    }

    @Transactional
    public RegisterMovementReponse registerMovement(RegisterMovementRequest request) {

        Product product = productRepository.findById(request.productId()).orElseThrow(
                    () -> new ProductNotFoundException("Producto no encontrado con id: " + request.productId())
        );

        switch (request.type()) {
            case IN -> product.increaseStock(request.quantity());
            case OUT -> product.decreaseStock(request.quantity());
        }

        Movement movement = Movement.builder()
                                .product(product)
                                .type(request.type())
                                .quantity(request.quantity())
                                .reason(request.reason())
                                .build();

    
        Movement newMovement = movementRepository.save(movement);

        if (request.type() == MovementType.OUT && product.isBelowMinimumStock()) {
            StockAlert stockAlert = StockAlert.builder()
                                        .productId(product.getId())
                                        .productName(product.getName())
                                        .currentStock(product.getCurrentStock())
                                        .minStock(product.getMinStock())
                                        .severity(calculateSeverity(product))
                                        .build();
            stockAlertRepository.save(stockAlert);
        }
        
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

    
    public Page<MovementResponse> findHistoryByProductId(Long productId,  Pageable pageable) {
        
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(
                    "Producto no encontrado con id: " + productId
            );
        }

        Page<Movement> movements = movementRepository.findByProduct_Id(productId, pageable);

        return movements.map(movement ->
                new MovementResponse(
                        movement.getId(),
                        movement.getProduct().getId(),
                        movement.getType(),
                        movement.getQuantity(),
                        movement.getReason(),
                        movement.getTimestamp()
                )
        );
    }


    private AlertSeverity calculateSeverity(Product product) {
        if (product.getCurrentStock() == 0) {
            return AlertSeverity.CRITICAL;
        }

        return AlertSeverity.LOW;
    }

    

}
