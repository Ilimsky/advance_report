package com.example.backend.service;

import com.example.backend.dto.DepartmentDTO;
import com.example.backend.dto.EmployeeDTO;
import com.example.backend.mapper.DepartmentMapper;
import com.example.backend.mapper.EmployeeMapper;
import com.example.backend.model.Department;
import com.example.backend.model.Employee;
import com.example.backend.repository.DepartmentRepository;
import com.example.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, EmployeeDTO, Long> implements EmployeeService {


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeMapper mapper) {
        super(repository, mapper);
    }

//    @Override
//    protected void updateEntity(Employee entity, EmployeeDTO dto) {
//        entity.setName(dto.getName());
//    }

}