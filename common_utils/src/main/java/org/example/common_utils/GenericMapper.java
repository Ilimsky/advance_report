package org.example.common_utils;

public interface GenericMapper<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}
