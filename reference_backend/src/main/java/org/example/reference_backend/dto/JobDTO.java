package org.example.reference_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JobDTO {

    private Long id;
    private String name;

    public JobDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public JobDTO(String name) {
        this.name = name;
    }

    public JobDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}