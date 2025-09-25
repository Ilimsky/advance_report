package org.example.warehouse_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private ProductDto product;
    private Integer quantity;
}