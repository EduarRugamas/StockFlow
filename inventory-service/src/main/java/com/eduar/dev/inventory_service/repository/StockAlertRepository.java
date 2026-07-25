package com.eduar.dev.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduar.dev.inventory_service.entity.StockAlert;

public interface StockAlertRepository extends JpaRepository<StockAlert, Long>{

}
