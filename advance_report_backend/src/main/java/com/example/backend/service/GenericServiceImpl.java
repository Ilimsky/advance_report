package com.example.backend.service;

import com.example.backend.mapper.GenericMapper;
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
    public Optional<D> getById(ID id) {
        return repository.findById(id).map(mapper::toDTO);
    }
}
