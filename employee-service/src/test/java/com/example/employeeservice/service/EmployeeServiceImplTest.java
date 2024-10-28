package com.example.employeeservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.employeeservice.dto.EmployeeDTO;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.repo.EmployeeRepository;
import com.example.employeeservice.exception.EmployeeNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализация моков
    }

    @Test
    void testCreateEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe");
        Employee employee = new Employee(1L, "John Doe");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);

        assertNotNull(createdEmployee);
        assertEquals(employeeDTO.getName(), createdEmployee.getName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetAllEmployees() {
        Employee employee = new Employee(1L, "John Doe");
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        assertEquals(1, employees.size());
        assertEquals(employee.getName(), employees.get(0).getName());
    }

    @Test
    void testGetEmployeeByIdFound() {
        Employee employee = new Employee(1L, "John Doe");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<EmployeeDTO> foundEmployee = employeeService.getEmployeeById(1L);

        assertTrue(foundEmployee.isPresent());
        assertEquals(employee.getName(), foundEmployee.get().getName());
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EmployeeDTO> foundEmployee = employeeService.getEmployeeById(1L);

        assertFalse(foundEmployee.isPresent());
    }

    @Test
    void testUpdateEmployeeFound() {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe Updated");
        Employee existingEmployee = new Employee(1L, "John Doe");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        EmployeeDTO updatedEmployee = employeeService.updateEmployee(1L, employeeDTO);

        assertEquals(employeeDTO.getName(), updatedEmployee.getName());
    }

    @Test
    void testUpdateEmployeeNotFound() {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe Updated");

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.updateEmployee(1L, employeeDTO);
        });

        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);
        
        assertDoesNotThrow(() -> employeeService.delete(1L));
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
void testCreateEmployeeWithExistingId() {
    EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe");
    Employee employee = new Employee(1L, "John Doe");

    when(employeeRepository.save(any(Employee.class))).thenThrow(new DataIntegrityViolationException("Duplicate ID"));

    Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
        employeeService.createEmployee(employeeDTO);
    });

    assertEquals("Duplicate ID", exception.getMessage());
}

@Test
void testUpdateEmployeeWithInvalidId() {
    EmployeeDTO employeeDTO = new EmployeeDTO(2L, "John Doe Updated");

    when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
        employeeService.updateEmployee(2L, employeeDTO);
    });

    assertEquals("Employee not found", exception.getMessage());
}

@Test
void testDeleteNonExistingEmployee() {
    doThrow(new EmptyResultDataAccessException(1)).when(employeeRepository).deleteById(1L);

    Exception exception = assertThrows(EmptyResultDataAccessException.class, () -> {
        employeeService.delete(1L);
    });

    assertEquals("No class com.example.employeeservice.entity.Employee entity with id 1 exists!", exception.getMessage());
}

@Test
void testGetAllEmployeesWhenEmpty() {
    when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

    List<EmployeeDTO> employees = employeeService.getAllEmployees();

    assertTrue(employees.isEmpty());
}


@Test
void testUpdateEmployeeWithInvalidData() {
    EmployeeDTO employeeDTO = new EmployeeDTO(1L, ""); // Пустое имя

    Employee existingEmployee = new Employee(1L, "John Doe");
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        employeeService.updateEmployee(1L, employeeDTO);
    });

    assertEquals("Employee name cannot be empty", exception.getMessage());
}

@Test
void testMapToEntity() {
    EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe");
    Employee employee = employeeService.mapToEntity(employeeDTO);

    assertNotNull(employee);
    assertEquals(employeeDTO.getId(), employee.getId());
    assertEquals(employeeDTO.getName(), employee.getName());
}

@Test
void testMapToDTO() {
    Employee employee = new Employee(1L, "John Doe");
    EmployeeDTO employeeDTO = employeeService.mapToDTO(employee);

    assertNotNull(employeeDTO);
    assertEquals(employee.getId(), employeeDTO.getId());
    assertEquals(employee.getName(), employeeDTO.getName());
}


}
