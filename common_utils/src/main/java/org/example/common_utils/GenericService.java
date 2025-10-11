package org.example.common_utils;

import java.util.List;
import java.util.Optional;

public interface GenericService <D, ID>{
    List<D> getAll();
    D create(D dto);
    Optional<D> getById(ID id);
    void delete(ID id);
    D update(ID id, D dto);
}
