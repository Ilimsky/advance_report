package org.example.inventory_backend.service;

import org.example.inventory_backend.dto.EmployeeDTO;
import org.example.inventory_backend.mapper.EmployeeMapper;
import org.example.inventory_backend.model.Employee;
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

//    private final EmployeeRepository employeeRepository;
//    private final EmployeeMapper employeeMapper;
//
//    @Autowired
//    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
//        this.employeeRepository = employeeRepository;
//        this.employeeMapper = employeeMapper;
//    }
//
//    @Override
//    public List<EmployeeDTO> getAllEmployees() {
//        return employeeRepository.findAll()
//                .stream()
//                .map(employeeMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
//        Employee employee = employeeMapper.toEntity(employeeDTO);
//        Employee savedEmployee = employeeRepository.save(employee);
//        return employeeMapper.toDTO(savedEmployee);
//    }
//
//    @Override
//    public Optional<EmployeeDTO> getEmployeeById(Long id) {
//        return employeeRepository.findById(id)
//                .map(employeeMapper::toDTO);
//    }
//
//    @Override
//    public void deleteEmployee(Long id) {
//        employeeRepository.deleteById(id);
//    }
//
//    @Override
//    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
//
//        employee.setName(employeeDTO.getName());
//
//        Employee updatedEmployee = employeeRepository.save(employee);
//        return employeeMapper.toDTO(updatedEmployee);
//    }
}