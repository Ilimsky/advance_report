package org.example.reference_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class RevizorDTO {

    private String name;
    private Long id;

    public RevizorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}