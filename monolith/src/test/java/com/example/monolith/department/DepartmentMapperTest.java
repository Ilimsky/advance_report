package com.example.monolith.department;

import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentMapperTest {

    private DepartmentMapper departmentMapper = new DepartmentMapper();

    @Test
    @QaseId(3)
    public void testToDTO(){
        Department department = new Department();
        department.setId(1L);
        department.setName("HR");

        DepartmentDTO departmentDTO = departmentMapper.toDTO(department);

        assertNotNull(departmentDTO);
        assertEquals(department.getId(), departmentDTO.getId());
        assertEquals(department.getName(), departmentDTO.getName());
    }

    @Test
    public void testToEntity(){
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setName("HR");

        Department department = departmentMapper.toEntity(departmentDTO);

        assertNotNull(department);
        assertEquals(departmentDTO.getId(), department.getId());
        assertEquals(departmentDTO.getName(), department.getName());
    }
}