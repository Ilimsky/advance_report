package org.example.inventory_backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "check_records")
public class CheckRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sked_id")
    private Sked sked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Sked getSked() {
        return sked;
    }

    public void setSked(Sked sked) {
        this.sked = sked;
    }
}