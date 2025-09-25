package org.example.warehouse_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean tableLayout;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ProductGroup group;

    private Integer stock = 0; // количество на складе
}