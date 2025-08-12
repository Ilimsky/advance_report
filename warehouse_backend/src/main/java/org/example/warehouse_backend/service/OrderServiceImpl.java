package org.example.warehouse_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.config.DemoUserFilter;
import org.example.warehouse_backend.dto.OrderDto;
import org.example.warehouse_backend.entity.CartItem;
import org.example.warehouse_backend.entity.OrderEntity;
import org.example.warehouse_backend.entity.User;
import org.example.warehouse_backend.exception.NotFoundException;
import org.example.warehouse_backend.mapper.OrderMapper;
import org.example.warehouse_backend.repository.CartItemRepository;
import org.example.warehouse_backend.repository.OrderRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderMapper orderMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public OrderDto placeOrder() {
        User user = DemoUserFilter.CURRENT.get();
        List<CartItem> cartItems = cartItemRepo.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new NotFoundException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setShipped(false);
        order.setItems(new ArrayList<>(cartItems));

        OrderEntity saved = orderRepo.save(order);
        cartItemRepo.deleteByUser(user);

        OrderDto dto = orderMapper.toDto(saved);
        messagingTemplate.convertAndSend("/topic/orders", dto);
        return dto;
    }

    @Override
    public List<OrderDto> getOrdersForCurrentUser() {
        User user = DemoUserFilter.CURRENT.get();
        return orderRepo.findByUser(user).stream().map(orderMapper::toDto).collect(Collectors.toList());
    }
}
