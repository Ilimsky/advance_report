package org.example.revizor_backend.mapper;

public interface GenericMapper<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}
