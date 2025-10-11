package com.example.backend.service;

import java.util.List;
import java.util.Optional;

public interface GenericService <D, ID>{
    List<D> getAll();
    Optional<D> getById(ID id);
}
