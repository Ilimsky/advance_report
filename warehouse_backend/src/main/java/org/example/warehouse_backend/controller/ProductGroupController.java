package org.example.warehouse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.dto.ProductGroupDto;
import org.example.warehouse_backend.service.ProductGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductGroupController {

    private final ProductGroupService service;

    @PostMapping
    public ProductGroupDto create(@RequestBody ProductGroupDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ProductGroupDto update(@PathVariable Long id, @RequestBody ProductGroupDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<ProductGroupDto> getAll() {
        return service.findAll();
    }
}
