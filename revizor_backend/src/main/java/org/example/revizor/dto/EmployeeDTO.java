package org.example.revizor.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EmployeeDTO {

    private Long id;
    private String name;

    public EmployeeDTO(Long id, String name) {
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