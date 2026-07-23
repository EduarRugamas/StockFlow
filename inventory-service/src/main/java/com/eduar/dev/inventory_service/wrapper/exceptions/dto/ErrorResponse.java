package com.eduar.dev.inventory_service.wrapper.exceptions.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
    Integer status,
    String error,
    String message,
    String path,
    LocalDateTime timestamp
) {}
