package org.example.warehouse_backend.dto;

public record OrderItemDto(Long id, Long offerId, Integer quantity, Double priceAtPurchase) {}
