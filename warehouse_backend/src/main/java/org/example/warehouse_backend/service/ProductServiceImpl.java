package org.example.warehouse_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.dto.ProductDto;
import org.example.warehouse_backend.entity.Product;
import org.example.warehouse_backend.entity.ProductGroup;
import org.example.warehouse_backend.exception.NotFoundException;
import org.example.warehouse_backend.mapper.ProductMapper;
import org.example.warehouse_backend.repository.ProductGroupRepository;
import org.example.warehouse_backend.repository.ProductRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final ProductGroupRepository groupRepo;
    private final ProductMapper mapper;
    private final SimpMessagingTemplate messagingTemplate; // для WebSocket

    @Override
    public ProductDto create(ProductDto dto) {
        ProductGroup g = groupRepo.findById(dto.getGroupId()).orElseThrow(() -> new NotFoundException("Group not found"));
        Product p = mapper.toEntity(dto, g);
        Product saved = productRepo.save(p);
        messagingTemplate.convertAndSend("/topic/products", mapper.toDto(saved));
        return mapper.toDto(saved);
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product p = productRepo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductGroup g = groupRepo.findById(dto.getGroupId()).orElseThrow(() -> new NotFoundException("Group not found"));
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setImageUrl(dto.getImageUrl());
        p.setGroup(g);
        p.setTableLayout(dto.isTableLayout());
        p.setStock(dto.getStock());
        Product saved = productRepo.save(p);
        messagingTemplate.convertAndSend("/topic/products", mapper.toDto(saved));
        return mapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
        messagingTemplate.convertAndSend("/topic/products", "deleted:" + id);
    }

    @Override
    public List<ProductDto> findAll(String search) {
        return productRepo.findByNameContainingIgnoreCase(search == null?"":search).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}