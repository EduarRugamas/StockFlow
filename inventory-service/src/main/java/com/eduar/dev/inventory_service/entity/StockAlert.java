package com.eduar.dev.inventory_service.entity;

import com.eduar.dev.inventory_service.wrapper.enums.AlertSeverity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mnt_stock_alert")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String productName;

    private Integer currentStock;

    private Integer minStock;

    @Enumerated(EnumType.STRING)
    private AlertSeverity severity;

}
