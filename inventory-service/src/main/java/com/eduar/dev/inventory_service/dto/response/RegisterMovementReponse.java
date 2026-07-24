package com.eduar.dev.inventory_service.dto.response;

import java.time.LocalDateTime;

import com.eduar.dev.inventory_service.wrapper.enums.MovementType;

public record RegisterMovementReponse(
    Long id,
    Long productId,
    String productSku,
    String productName,
    MovementType type,
    Integer quantity,
    String reason,
    LocalDateTime timestamp
) {}
