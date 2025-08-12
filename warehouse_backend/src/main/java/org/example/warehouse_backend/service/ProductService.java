package org.example.warehouse_backend.service;

import org.example.warehouse_backend.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto dto);
    ProductDto update(Long id, ProductDto dto);
    void delete(Long id);
    List<ProductDto> findAll(String search);
}