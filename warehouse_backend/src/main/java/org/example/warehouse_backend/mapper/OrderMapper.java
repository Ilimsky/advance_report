package org.example.warehouse_backend.mapper;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.dto.OrderDto;
import org.example.warehouse_backend.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final CartItemMapper cartItemMapper;

    public OrderDto toDto(OrderEntity entity) {
        if (entity == null) return null;
        return new OrderDto(
                entity.getId(),
                entity.getUser() != null ? entity.getUser().getId() : null,
                entity.getCreatedAt(),
                entity.isShipped(),
                entity.getItems() == null ? null :
                        entity.getItems().stream().map(cartItemMapper::toDto).collect(Collectors.toList())
        );
    }
}
