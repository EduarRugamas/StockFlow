package com.eduar.dev.inventory_service.dto.response;

import java.util.List;

public record StockAlertPageResponse(
    List<StockAlertResponse> alerts,
    int page,
    int size,
    long totalElements,
    int totalPages,
    String message,
    boolean fallback
) {}
