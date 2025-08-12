package org.example.warehouse_backend.service;

import org.example.warehouse_backend.dto.CartItemDto;

import java.util.List;

public interface CartItemService {
    List<CartItemDto> getCartForCurrentUser();
    CartItemDto addToCart(CartItemDto dto);
    void removeFromCart(Long id);
    void clearCart();
}
