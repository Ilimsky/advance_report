package org.example.warehouse_backend.repository;

import org.example.warehouse_backend.entity.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
}