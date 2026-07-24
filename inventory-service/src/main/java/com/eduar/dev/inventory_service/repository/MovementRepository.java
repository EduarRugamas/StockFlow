package com.eduar.dev.inventory_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eduar.dev.inventory_service.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    Page<Movement> findByProduct_Id(
            Long productId,
            Pageable pageable
    );

}
