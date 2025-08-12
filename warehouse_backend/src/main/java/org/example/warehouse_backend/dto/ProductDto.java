package org.example.warehouse_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Long groupId;
    private boolean tableLayout;
    private Integer stock;
}