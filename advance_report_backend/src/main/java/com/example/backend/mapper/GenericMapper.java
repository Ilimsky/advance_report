package com.example.backend.mapper;

public interface GenericMapper<E, D> {
    D toDTO(E entity);
}
