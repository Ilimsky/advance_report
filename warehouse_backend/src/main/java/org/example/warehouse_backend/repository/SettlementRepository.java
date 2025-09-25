package org.example.warehouse_backend.repository;

import org.example.warehouse_backend.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {}