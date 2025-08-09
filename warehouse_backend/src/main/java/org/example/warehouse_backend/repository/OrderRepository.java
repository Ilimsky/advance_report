package org.example.warehouse_backend.repository;

import org.example.warehouse_backend.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}