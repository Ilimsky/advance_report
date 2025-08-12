package org.example.warehouse_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String colorHex;
    private String imageUrl;
    private boolean tableLayout; // true = table, false = tile

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Product> products;
}