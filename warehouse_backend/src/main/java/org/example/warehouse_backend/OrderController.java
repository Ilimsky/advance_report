package org.example.warehouse_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/{id}/status")
    public OrderDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        return orderService.updateOrderStatus(id, request.get("status"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}