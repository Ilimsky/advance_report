package org.example.warehouse_backend.service;

import org.example.warehouse_backend.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder();
    List<OrderDto> getOrdersForCurrentUser();
}
