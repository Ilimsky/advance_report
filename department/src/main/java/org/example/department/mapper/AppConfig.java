package org.example.department.mapper;

import org.example.department.dto.DepartmentDTO;
import org.example.department.entity.Department;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        // Настройка маппинга DTO -> Entity
        modelMapper.typeMap(DepartmentDTO.class, Department.class).addMappings(mapper -> {
            mapper.map(DepartmentDTO::getDepartmentDTOId, Department::setDepartmentId);
            mapper.map(DepartmentDTO::getDepartmentDTOName, Department::setDepartmentName);
        });

        // Настройка маппинга Entity -> DTO
        modelMapper.typeMap(Department.class, DepartmentDTO.class).addMappings(mapper -> {
            mapper.map(Department::getDepartmentId, DepartmentDTO::setDepartmentDTOId);
            mapper.map(Department::getDepartmentName, DepartmentDTO::setDepartmentDTOName);
        });
        return modelMapper;
    }

}
