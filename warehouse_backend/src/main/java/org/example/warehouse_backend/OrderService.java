package org.example.warehouse_backend;

import org.example.warehouse_backend.repository.OrderRepository;
import org.example.warehouse_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setCustomerName(orderDto.getCustomerName());
        order.setCustomerPhone(orderDto.getCustomerPhone());
        order.setCustomerEmail(orderDto.getCustomerEmail());
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        List<OrderItem> items = orderDto.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));

                    if (product.getStockQuantity() < itemDto.getQuantity()) {
                        throw new RuntimeException("Not enough stock for product: " + product.getName());
                    }

                    product.setStockQuantity(product.getStockQuantity() - itemDto.getQuantity());
                    productRepository.save(product);

                    OrderItem item = new OrderItem();
                    item.setProduct(product);
                    item.setQuantity(itemDto.getQuantity());
                    item.setPricePerUnit(product.getPrice());
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(items);
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAllWithItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerPhone(order.getCustomerPhone());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().name());

        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(item -> {
                    OrderItemDto itemDto = new OrderItemDto();
                    itemDto.setProductId(item.getProduct().getId());
                    itemDto.setProductName(item.getProduct().getName());
                    itemDto.setQuantity(item.getQuantity());
                    itemDto.setPricePerUnit(item.getPricePerUnit());
                    return itemDto;
                })
                .collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }

    @Transactional
    public OrderDto updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            order.setStatus(OrderStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        Order updatedOrder = orderRepository.save(order);
        return convertToDto(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Восстанавливаем количество товаров
        restoreProductQuantities(order);

        orderRepository.delete(order);
    }

    private void restoreProductQuantities(Order order) {
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
    }

    public boolean orderExists(Long id) {
        return orderRepository.existsById(id);
    }
}
