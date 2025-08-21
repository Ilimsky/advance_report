package org.example.inventory_backend.service;

import org.example.inventory_backend.dto.EmployeeDTO;
import org.example.inventory_backend.model.Employee;
import org.example.inventory_backend.mapper.EmployeeMapper;
import org.example.inventory_backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, EmployeeDTO, Long> implements EmployeeService {


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void updateEntity(Employee entity, EmployeeDTO dto) {
        entity.setName(dto.getName());
    }

}