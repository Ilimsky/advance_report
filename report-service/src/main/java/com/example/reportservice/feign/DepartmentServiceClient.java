package com.example.reportservice.feign;

import com.example.departmentservice.dto.DepartmentDTO;
import com.example.departmentservice.entity.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "department-service")
public interface DepartmentServiceClient {
    @GetMapping("/api/departments/{id}")
    DepartmentDTO getDepartment(@PathVariable("id") Long id);
}
