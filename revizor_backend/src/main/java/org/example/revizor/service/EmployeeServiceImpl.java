package org.example.revizor.service;

import org.example.revizor.dto.EmployeeDTO;
import org.example.revizor.mapper.EmployeeMapper;
import org.example.revizor.model.Employee;
import org.example.revizor.repository.EmployeeRepository;
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