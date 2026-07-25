package com.eduar.dev.inventory_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eduar.dev.inventory_service.dto.response.StockAlertResponse;
import com.eduar.dev.inventory_service.repository.StockAlertRepository;

@Service
public class StockAlertService {

    private final StockAlertRepository stockAlertRepository;

    public StockAlertService(StockAlertRepository stockAlertRepository) {
        this.stockAlertRepository = stockAlertRepository;
    }

    
    public Page<StockAlertResponse> getAllStockAlerts(Pageable pageable) {
        return stockAlertRepository.findAll(pageable).map(alert -> new StockAlertResponse(
            alert.getId(),
            alert.getProductId(), 
            alert.getProductName(), 
            alert.getCurrentStock(), 
            alert.getMinStock(), 
            alert.getSeverity())
        );
    }

}
