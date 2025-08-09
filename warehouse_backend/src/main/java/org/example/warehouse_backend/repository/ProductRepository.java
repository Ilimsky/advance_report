package org.example.warehouse_backend.repository;

import org.example.warehouse_backend.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}