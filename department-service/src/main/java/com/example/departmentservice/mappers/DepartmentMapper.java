package com.example.departmentservice.mappers;

import com.example.departmentservice.dto.DepartmentDTO;
import com.example.departmentservice.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    // Преобразование из сущности в DTO
    public DepartmentDTO toDTO(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getLastModified(),
                department.isSynced(),
                department.isNewDepartment(),
                department.isModified()
        );
    }

    // Преобразование из DTO в сущность
    public Department toEntity(DepartmentDTO departmentDTO) {
        if (departmentDTO == null) {
            return null;
        }

        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());
        department.setLastModified(departmentDTO.getLastModified());
        department.setSynced(departmentDTO.isSynced());
        department.setNewDepartment(departmentDTO.isNewDepartment());
        department.setModified(departmentDTO.isModified());

        return department;
    }

    // Преобразование из списка сущностей в список DTO
    public List<DepartmentDTO> toDTOList(List<Department> departments) {
        if (departments == null) {
            return null;
        }
        return departments.stream()
                .map(this::toDTO)  // Используем this для вызова нестатического метода
                .collect(Collectors.toList());
    }

    // Преобразование из списка DTO в список сущностей
    public List<Department> toEntityList(List<DepartmentDTO> departmentDTOs) {
        if (departmentDTOs == null) {
            return null;
        }
        return departmentDTOs.stream()
                .map(this::toEntity)  // Используем this для вызова нестатического метода
                .collect(Collectors.toList());
    }
}