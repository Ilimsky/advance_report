package org.example.warehouse_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Offer offer;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double priceAtPurchase; // цена оффера на момент покупки

    @ManyToOne(optional = false)
    private OrderEntity order;
}