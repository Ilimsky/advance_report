package org.example.warehouse_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.warehouse_backend.dto.ProductGroupDto;
import org.example.warehouse_backend.entity.ProductGroup;
import org.example.warehouse_backend.exception.NotFoundException;
import org.example.warehouse_backend.mapper.ProductGroupMapper;
import org.example.warehouse_backend.repository.ProductGroupRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductGroupServiceImpl implements ProductGroupService {

    private final ProductGroupRepository groupRepo;
    private final ProductGroupMapper mapper;

    @Override
    public ProductGroupDto create(ProductGroupDto dto) {
        ProductGroup entity = new ProductGroup(null, dto.getName(), dto.getColorHex(), dto.getImageUrl(), dto.isTableLayout(), new ArrayList<>());
        return mapper.toDto(groupRepo.save(entity));
    }

    @Override
    public ProductGroupDto update(Long id, ProductGroupDto dto) {
        ProductGroup g = groupRepo.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
        g.setName(dto.getName());
        g.setColorHex(dto.getColorHex());
        g.setImageUrl(dto.getImageUrl());
        g.setTableLayout(dto.isTableLayout());
        return mapper.toDto(groupRepo.save(g));
    }

    @Override
    public void delete(Long id) {
        groupRepo.deleteById(id);
    }

    @Override
    public List<ProductGroupDto> findAll() {
        return groupRepo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
