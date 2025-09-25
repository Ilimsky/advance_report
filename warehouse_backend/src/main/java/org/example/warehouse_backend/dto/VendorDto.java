package org.example.warehouse_backend.dto;

public record VendorDto(Long id, String name, String legalName, String taxId, String contactEmail, String contactPhone,
                        boolean active) {
}
