package org.example.inventory_backend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkedDTO {

    private Long id;
    private Long departmentId;
    private String departmentName;
    private Long employeeId;

    private Long departmentIdentifier;
    private Long employeeIdentifier;

    private String assetCategory;

    private LocalDate dateReceived;
    private String skedNumber;
    private String itemName;
    private String serialNumber;
    private int count;
    private String measure;
    private Double price;
    private String place;
    private String comments;
}