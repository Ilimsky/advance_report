package org.example.inventory_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Employee employee;
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
    private boolean available = false;
    private boolean numberReleased = false; // Флаг того, что номер освобожден!

    @OneToMany(mappedBy = "sked", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SkedHistory> history = new ArrayList<>();
}