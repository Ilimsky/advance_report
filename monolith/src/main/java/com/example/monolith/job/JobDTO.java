package com.example.monolith.job;

public class JobDTO {
    private Long id;
    private String name;


    public JobDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public JobDTO() {
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
