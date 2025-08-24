package org.example.inventory_backend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkedDTO implements Serializable {

    private static final long serialVersionUID = 1;

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

    private boolean available;
    private boolean isActive; // Новое поле

    private boolean numberReleased = false; // Флаг того, что номер освобожден!
}