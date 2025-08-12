package org.example.warehouse_backend.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private boolean shipped;
    private List<CartItemDto> items;
}