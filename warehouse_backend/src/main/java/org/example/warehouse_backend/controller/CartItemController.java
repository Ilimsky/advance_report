package org.example.warehouse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.dto.CartItemDto;
import org.example.warehouse_backend.service.CartItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService service;

    @GetMapping
    public List<CartItemDto> getCart() {
        return service.getCartForCurrentUser();
    }

    @PostMapping
    public CartItemDto add(@RequestBody CartItemDto dto) {
        return service.addToCart(dto);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        service.removeFromCart(id);
    }

    @DeleteMapping
    public void clear() {
        service.clearCart();
    }
}
