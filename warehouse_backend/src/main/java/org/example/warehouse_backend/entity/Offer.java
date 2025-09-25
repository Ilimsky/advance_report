package org.example.warehouse_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private Vendor vendor;

    @Column(nullable = false)
    private Double price;

    private Integer stock; // остаток именно у продавца

    @Enumerated(EnumType.STRING)
    private OfferStatus status = OfferStatus.ACTIVE; // ACTIVE, PAUSED, ARCHIVED

    private Double commissionRate; // % комиссии агрегатора по этому офферу

    private String deliveryOptions; // JSON/строка с тарифами/сроками
}