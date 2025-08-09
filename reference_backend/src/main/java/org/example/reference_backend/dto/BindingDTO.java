package org.example.reference_backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class BindingDTO {
    private Long id;
    private EmployeeDTO employee;
    private DepartmentDTO department;
//    private JobDTO job;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }
}