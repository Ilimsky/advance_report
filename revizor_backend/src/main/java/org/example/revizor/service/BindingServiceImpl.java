package org.example.revizor.service;

import org.example.revizor.dto.BindingDTO;
import org.example.revizor.exception.EntityNotFoundException;
import org.example.revizor.mapper.BindingMapper;
import org.example.revizor.model.Binding;
import org.example.revizor.model.Department;
import org.example.revizor.model.Employee;
import org.example.revizor.repository.BindingRepository;
import org.example.revizor.repository.DepartmentRepository;
import org.example.revizor.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BindingServiceImpl implements BindingService {

    private final BindingRepository bindingRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final BindingMapper bindingMapper;

    public BindingServiceImpl(BindingRepository bindingRepository,
                              DepartmentRepository departmentRepository,
                              EmployeeRepository employeeRepository,
                              BindingMapper bindingMapper) {
        this.bindingRepository = bindingRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.bindingMapper = bindingMapper;
    }

    @Override
    public BindingDTO createBinding(BindingDTO bindingDTO) {
        Department department = departmentRepository.findById(bindingDTO.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Department", bindingDTO.getDepartment().getId()));
        Employee employee = employeeRepository.findById(bindingDTO.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", bindingDTO.getEmployee().getId()));

        Binding binding = bindingMapper.toEntity(bindingDTO, department, employee);

        Binding savedBinding = bindingRepository.save(binding);

        return bindingMapper.toDTO(savedBinding);
    }

    @Override
    public List<BindingDTO> getAllBindings() {
        return bindingRepository.findAll().stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BindingDTO> getBindingsByIds(Long departmentId, Long jobId, Long employeeId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        List<Binding> bindings = bindingRepository
                .findByDepartmentIdAndEmployeeId(departmentId, employeeId);

        return bindings.stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BindingDTO getBindingById(Long bindingId) {
        Binding binding = bindingRepository.findById(bindingId)
                .orElseThrow(() -> new EntityNotFoundException("Binding", bindingId));
        return bindingMapper.toDTO(binding);
    }

    @Override
    public BindingDTO updateBinding(Long bindingId, BindingDTO updatedBindingDTO) {
        Binding existingBinding = bindingRepository.findById(bindingId)
                .orElseThrow(() -> new EntityNotFoundException("Binding", bindingId));

        Department department = departmentRepository.findById(updatedBindingDTO.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Department", updatedBindingDTO.getDepartment().getId()));
        Employee employee = employeeRepository.findById(updatedBindingDTO.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", updatedBindingDTO.getEmployee().getId()));

        // Обновление привязки
        existingBinding.setDepartment(department);
        existingBinding.setEmployee(employee);

        Binding updatedBinding = bindingRepository.save(existingBinding);

        return bindingMapper.toDTO(updatedBinding);
    }

    @Override
    public void deleteBinding(Long bindingId) {
        if (!bindingRepository.existsById(bindingId)) {
            throw new EntityNotFoundException("Binding", bindingId);
        }
        bindingRepository.deleteById(bindingId);
    }

    @Override
    public void deleteBindingsByIds(Long departmentId, Long jobId, Long employeeId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        List<Binding> bindings = bindingRepository
                .findByDepartmentIdAndEmployeeId(departmentId, employeeId);

        bindingRepository.deleteAll(bindings);
    }
}
