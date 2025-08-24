package org.example.inventory_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SkedHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Sked sked;

    private String actionType; // "CREATE", "UPDATE", "TRANSFER", "WRITE_OFF"
    private LocalDateTime actionDate;
    private String performedBy = "SYSTEM"; // Пока фиксированное значение

    // Данные для истории
    @Lob
    private String previousData; // JSON с данными до изменения

    @Lob
    private String newData;      // JSON с данными после изменения
    private String reason;

    @PrePersist
    public void prePersist() {
        this.actionDate = LocalDateTime.now();
    }
}