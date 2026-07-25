package com.eduar.dev.inventory_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduar.dev.inventory_service.dto.response.StockAlertResponse;
import com.eduar.dev.inventory_service.service.StockAlertService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping(path = "/api/v1/alerts")
public class StockAlertsController {

    private final StockAlertService stockAlertService;

    public StockAlertsController(StockAlertService stockAlertService) {
        this.stockAlertService = stockAlertService;
    }

    
    @GetMapping(path = "")
    public ResponseEntity<Page<StockAlertResponse>> getAllStockAlerts(
         @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC
        )
        Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(stockAlertService.getAllStockAlerts(pageable));
    }
    

}
