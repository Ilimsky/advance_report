package org.example.inventory_backend.repository;

import org.example.inventory_backend.model.CheckRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckRecordRepository extends JpaRepository<CheckRecord, Long> {
    List<CheckRecord> findBySkedId(Integer skedId);
}