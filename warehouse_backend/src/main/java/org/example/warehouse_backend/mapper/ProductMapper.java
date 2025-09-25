package org.example.warehouse_backend.mapper;

import org.example.warehouse_backend.dto.ProductDto;
import org.example.warehouse_backend.entity.Product;
import org.example.warehouse_backend.entity.ProductGroup;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product p) {
        if (p == null) return null;
        return new ProductDto(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getImageUrl(), p.getGroup()==null?null:p.getGroup().getId(), p.isTableLayout(), p.getStock());
    }

    public Product toEntity(ProductDto d, ProductGroup g) {
        Product p = new Product();
        p.setId(d.getId());
        p.setName(d.getName());
        p.setDescription(d.getDescription());
        p.setPrice(d.getPrice());
        p.setImageUrl(d.getImageUrl());
        p.setGroup(g);
        p.setTableLayout(d.isTableLayout());
        p.setStock(d.getStock()==null?0:d.getStock());
        return p;
    }
}