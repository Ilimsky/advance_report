package org.example.warehouse_backend.mapper;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.dto.CartItemDto;
import org.example.warehouse_backend.entity.CartItem;
import org.example.warehouse_backend.entity.Product;
import org.example.warehouse_backend.entity.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemMapper {

    private final ProductMapper productMapper;

    public CartItemDto toDto(CartItem entity) {
        if (entity == null) return null;
        return new CartItemDto(
                entity.getId(),
                productMapper.toDto(entity.getProduct()),
                entity.getQuantity()
        );
    }

    public CartItem toEntity(CartItemDto dto, Product product, User user) {
        if (dto == null) return null;
        CartItem item = new CartItem();
        item.setId(dto.getId());
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setUser(user);
        return item;
    }
}
