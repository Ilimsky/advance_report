package org.example.reference_backend.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " with ID " + id + " not found");
    }

}
