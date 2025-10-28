package org.example.usermanagementservice;

import org.example.departmentservice.Department;
import org.example.departmentservice.DepartmentMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, DepartmentMapper.class})
public interface UserDepartmentAssignmentMapper {


    UserDepartmentAssignmentDTO toDTO(UserDepartmentAssignment binding);

    @Mapping(target = "id", ignore = true)
    UserDepartmentAssignment toEntity(UserDepartmentAssignmentDTO dto,
                                      @Context Department department,
                                      @Context User user);
}