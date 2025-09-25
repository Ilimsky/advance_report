package org.example.warehouse_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.config.DemoUserFilter;
import org.example.warehouse_backend.dto.CartItemDto;
import org.example.warehouse_backend.entity.CartItem;
import org.example.warehouse_backend.entity.Product;
import org.example.warehouse_backend.entity.User;
import org.example.warehouse_backend.exception.NotFoundException;
import org.example.warehouse_backend.mapper.CartItemMapper;
import org.example.warehouse_backend.repository.CartItemRepository;
import org.example.warehouse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;
    private final CartItemMapper mapper;

    @Override
    public List<CartItemDto> getCartForCurrentUser() {
        User user = DemoUserFilter.CURRENT.get();
        return cartItemRepo.findByUser(user).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CartItemDto addToCart(CartItemDto dto) {
        User user = DemoUserFilter.CURRENT.get();
        Product product = productRepo.findById(dto.getProduct().getId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        CartItem entity = mapper.toEntity(dto, product, user);
        return mapper.toDto(cartItemRepo.save(entity));
    }

    @Override
    public void removeFromCart(Long id) {
        cartItemRepo.deleteById(id);
    }

    @Override
    public void clearCart() {
        User user = DemoUserFilter.CURRENT.get();
        cartItemRepo.deleteByUser(user);
    }
}
