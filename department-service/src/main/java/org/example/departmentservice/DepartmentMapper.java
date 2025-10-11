package org.example.departmentservice;

import org.example.common_utils.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends GenericMapper<Department, DepartmentDTO> {

    @Override
    DepartmentDTO toDTO(Department department);

    @Override
    Department toEntity(DepartmentDTO  departmentDTO);
}
