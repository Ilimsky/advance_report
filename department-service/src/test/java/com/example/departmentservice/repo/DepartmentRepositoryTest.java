package com.example.departmentservice.repo;

import com.example.departmentservice.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DepartmentRepositoryTest {

//    @Autowired
//    private DepartmentRepository departmentRepository;

//    @Test
//    void saveAndRetrieveDepartment_ShouldSaveAndRetrieve(){
//        Department department = new Department();
//        department.setName("Finance");
//        department.setLastModified(LocalDateTime.now());
//        department.setNewDepartment(true);
//
//        Department savedDepartment = departmentRepository.save(department);
//
//        assertNotNull(savedDepartment.getId(), "ID департамента должен быть присвоен после сохранения");
//        assertEquals("Finance", savedDepartment.getName(), "Название департамента должно совпадать");
//
//        Optional<Department> retrievedDepartment = departmentRepository.findById(savedDepartment.getId());
//        assertTrue(retrievedDepartment.isPresent(), "Департамент должен быть найден по ID");
//        assertEquals(savedDepartment.getId(), retrievedDepartment.get().getId(), "ID сохраненного и извлеченного департамента должны совпадать");
//    }
}