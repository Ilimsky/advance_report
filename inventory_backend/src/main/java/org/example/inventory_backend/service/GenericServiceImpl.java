package org.example.inventory_backend.service;

import org.example.inventory_backend.mapper.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GenericServiceImpl<E, D, ID> implements GenericService<D, ID> {
    protected final JpaRepository<E, ID> repository;
    protected final GenericMapper<E, D> mapper;

    protected GenericServiceImpl(JpaRepository<E, ID> repository, GenericMapper<E, D> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<D> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public D create(D dto) {
        E entity = mapper.toEntity(dto);
        E savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public Optional<D> getById(ID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public D update(ID id, D dto) {
        E entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        updateEntity(entity, dto);
        E updateEntity = repository.save(entity);
        return mapper.toDTO(updateEntity);
    }

    protected abstract void updateEntity(E entity, D dto);
}
