package org.example.warehouse_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Vendor vendor;

    private LocalDate fromDate;
    private LocalDate toDate;

    private Double grossSales;   // сумма продаж
    private Double commission;   // удержанная комиссия агрегатора
    private Double netPayout;    // к выплате продавцу

    private boolean paid;
    private LocalDateTime paidAt;
}