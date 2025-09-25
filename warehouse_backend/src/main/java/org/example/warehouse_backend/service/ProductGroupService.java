package org.example.warehouse_backend.service;

import org.example.warehouse_backend.dto.ProductGroupDto;

import java.util.List;

public interface ProductGroupService {
    ProductGroupDto create(ProductGroupDto dto);
    ProductGroupDto update(Long id, ProductGroupDto dto);
    void delete(Long id);
    List<ProductGroupDto> findAll();
}
