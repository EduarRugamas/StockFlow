package com.eduar.dev.inventory_service.wrapper.indicator;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

import com.eduar.dev.inventory_service.repository.ProductRepository;


@Component("inventory")
public class CustomHealthIndicator implements HealthIndicator {

    private final ProductRepository productRepository;

    public CustomHealthIndicator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private static final double CRITICAL_THRESHOLD_PERCENTAGE = 20.0;

    @Override
    public @Nullable Health health() {

        long totalProducts = productRepository.count();
        long criticalProducts = productRepository.countCriticalProducts();

        double criticalPercentage = calculatePercentage(
                criticalProducts,
                totalProducts
        );

        if (criticalPercentage > CRITICAL_THRESHOLD_PERCENTAGE) {
            return Health.down()
                    .withDetail(
                            "message",
                            "Más del 20% de los productos se encuentran en estado crítico"
                    )
                    .withDetail("totalProducts", totalProducts)
                    .withDetail("criticalProducts", criticalProducts)
                    .withDetail(
                            "criticalPercentage",
                            String.format("%.2f%%", criticalPercentage)
                    )
                    .withDetail(
                            "thresholdPercentage",
                            CRITICAL_THRESHOLD_PERCENTAGE + "%"
                    )
                    .build();
        }

        return Health.up()
                .withDetail(
                        "message",
                        "El porcentaje de productos críticos está dentro del límite permitido"
                )
                .withDetail("totalProducts", totalProducts)
                .withDetail("criticalProducts", criticalProducts)
                .withDetail(
                        "criticalPercentage",
                        String.format("%.2f%%", criticalPercentage)
                )
                .withDetail(
                        "thresholdPercentage",
                        CRITICAL_THRESHOLD_PERCENTAGE + "%"
                )
                .build();
    }


    private double calculatePercentage(long criticalProducts, long totalProducts) {
        if (totalProducts == 0) {
            return 0.0;
        }

        return ((double) criticalProducts / totalProducts) * 100;
    }

}
