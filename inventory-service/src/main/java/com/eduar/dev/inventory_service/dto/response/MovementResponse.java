package com.eduar.dev.inventory_service.dto.response;

import java.time.LocalDateTime;

import com.eduar.dev.inventory_service.wrapper.enums.MovementType;

public record MovementResponse(
    Long id,
    Long productId,
    MovementType type,
    Integer quantity,
    String reason,
    LocalDateTime timestamp
) {}
