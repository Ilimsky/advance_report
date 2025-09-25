package org.example.warehouse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.dto.ProductDto;
import org.example.warehouse_backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ProductDto create(@RequestBody ProductDto dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id, @RequestBody ProductDto dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping
    public List<ProductDto> getAll(@RequestParam(required = false) String search) { return service.findAll(search); }
}