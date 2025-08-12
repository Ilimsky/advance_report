package org.example.warehouse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.dto.OrderDto;
import org.example.warehouse_backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public OrderDto placeOrder() {
        return service.placeOrder();
    }

    @GetMapping
    public List<OrderDto> getOrders() {
        return service.getOrdersForCurrentUser();
    }
}
