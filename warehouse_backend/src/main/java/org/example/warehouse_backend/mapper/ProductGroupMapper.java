package org.example.warehouse_backend.mapper;

import org.example.warehouse_backend.dto.ProductGroupDto;
import org.example.warehouse_backend.entity.ProductGroup;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductGroupMapper {
    private final ProductMapper productMapper;
    public ProductGroupMapper(ProductMapper productMapper) { this.productMapper = productMapper; }

    public ProductGroupDto toDto(ProductGroup g) {
        if (g == null) return null;
        return new ProductGroupDto(g.getId(), g.getName(), g.getColorHex(), g.getImageUrl(), g.isTableLayout(),
                g.getProducts() == null ? null : g.getProducts().stream().map(productMapper::toDto).collect(Collectors.toList()));
    }
}