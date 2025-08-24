package org.example.inventory_backend.repository;

import org.example.inventory_backend.model.SkedHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkedHistoryRepository extends JpaRepository<SkedHistory, Long> {
    List<SkedHistory> findBySkedIdOrderByActionDateDesc(Long skedId);
}