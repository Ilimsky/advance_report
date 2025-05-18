package org.example.inventory_backend.dto;

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
    private Long jobId;
    private Long employeeId;

    private Long departmentIdentifier;
    private Long jobIdentifier;
    private Long employeeIdentifier;

    private LocalDate dateReceived;
    private int skedNumber;
    private String itemName;
    private String serialNumber;
    private int count;
    private String measure;
    private Double price;
    private String place;

    private String comments;
}