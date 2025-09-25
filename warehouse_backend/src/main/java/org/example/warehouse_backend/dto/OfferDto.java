package org.example.warehouse_backend.dto;

public record OfferDto(Long id, Long productId, Long vendorId, Double price, Integer stock, String status,
                       Double commissionRate, String deliveryOptions) {
}
