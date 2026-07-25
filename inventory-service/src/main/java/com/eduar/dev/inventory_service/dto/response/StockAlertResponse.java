package com.eduar.dev.inventory_service.dto.response;

import com.eduar.dev.inventory_service.wrapper.enums.AlertSeverity;

public record StockAlertResponse(
    Long id,
    Long productId,
    String productName,
    Integer currentStock,
    Integer minStock,
    AlertSeverity severity
) {}
