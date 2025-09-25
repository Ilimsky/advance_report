package org.example.warehouse_backend.repository;

import org.example.warehouse_backend.entity.OrderEntity;
import org.example.warehouse_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser(User user);
}