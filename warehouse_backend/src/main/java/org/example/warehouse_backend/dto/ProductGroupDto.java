package org.example.warehouse_backend.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroupDto {
    private Long id;
    private String name;
    private String colorHex;
    private String imageUrl;
    private boolean tableLayout;
    private List<ProductDto> products;
}