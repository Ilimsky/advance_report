package org.example.inventory_backend.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private Long newDepartmentId;
    private String reason;
}