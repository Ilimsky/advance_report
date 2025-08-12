package org.example.warehouse_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime createdAt;

    private boolean shipped = false; // отгрузка со склада

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> items;
}