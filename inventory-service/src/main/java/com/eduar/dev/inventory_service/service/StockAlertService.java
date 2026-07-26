package com.eduar.dev.inventory_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eduar.dev.inventory_service.dto.response.StockAlertPageResponse;
import com.eduar.dev.inventory_service.dto.response.StockAlertResponse;
import com.eduar.dev.inventory_service.repository.StockAlertRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockAlertService {

    private final StockAlertRepository stockAlertRepository;

    public StockAlertService(StockAlertRepository stockAlertRepository) {
        this.stockAlertRepository = stockAlertRepository;
    }


    @CircuitBreaker(
            name = "alertsCircuitBreaker",
            fallbackMethod = "getAllStockAlertsFallback"
    )
    public StockAlertPageResponse getAllStockAlerts(Pageable pageable) {

        Page<StockAlertResponse> alertsPage =
                stockAlertRepository.findAll(pageable)
                        .map(alert -> new StockAlertResponse(
                                alert.getId(),
                                alert.getProductId(),
                                alert.getProductName(),
                                alert.getCurrentStock(),
                                alert.getMinStock(),
                                alert.getSeverity()
                        ));

        return new StockAlertPageResponse(
                alertsPage.getContent(),
                alertsPage.getNumber(),
                alertsPage.getSize(),
                alertsPage.getTotalElements(),
                alertsPage.getTotalPages(),
                "Alertas consultadas correctamente",
                false
        );
    }

    private StockAlertPageResponse getAllStockAlertsFallback(Pageable pageable, Throwable exception) {
        log.warn(
                "Se ejecutó el fallback de alertas. Causa: {}",
                exception.getMessage()
        );

        return new StockAlertPageResponse(
                List.of(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                0,
                0,
                "El servicio de alertas no está disponible temporalmente. ",
                true
        );
    }

}
