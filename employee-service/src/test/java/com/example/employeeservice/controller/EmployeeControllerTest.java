package com.example.employeeservice.controller;

import com.example.employeeservice.dto.EmployeeDTO;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import com.example.employeeservice.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe");

        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).createEmployee(any(EmployeeDTO.class));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe");
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employeeDTO));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void testGetEmployeeById_Found() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe");
        when(employeeService.getEmployeeById(anyLong())).thenReturn(Optional.of(employeeDTO));

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).getEmployeeById(anyLong());
    }

    @Test
    public void testGetEmployeeById_NotFound() throws Exception {
        when(employeeService.getEmployeeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployeeById(anyLong());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John Doe");
        when(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(put("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).updateEmployee(anyLong(), any(EmployeeDTO.class));
    }

    @Test
    public void testUpdateEmployee_NotFound() throws Exception {
        when(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class))).thenThrow(new EmployeeNotFoundException());

        mockMvc.perform(put("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"John Doe\"}"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).updateEmployee(anyLong(), any(EmployeeDTO.class));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).delete(anyLong());

        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).delete(anyLong());
    }

    @Test
    public void testDeleteEmployee_NotFound() throws Exception {
        doThrow(new EmployeeNotFoundException()).when(employeeService).delete(anyLong());

        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).delete(anyLong());
    }
}
