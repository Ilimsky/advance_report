package com.example.monolith.employee;

public class EmployeeDTO {
    private Long id;
    private String name;


    public EmployeeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EmployeeDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
