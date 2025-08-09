package org.example.revizor.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RevizorDTO {

    private String name;
    private Long id;

    public RevizorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RevizorDTO() {
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