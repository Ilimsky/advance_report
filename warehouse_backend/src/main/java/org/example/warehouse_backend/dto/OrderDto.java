package org.example.warehouse_backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(Long id, Long userId, String status, Double subtotal, Double deliveryFee, Double total,
                       List<OrderItemDto> items) {
}