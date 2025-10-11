package org.example.employee_departmentservice;

import org.example.common_utils.EntityNotFoundException;
import org.example.departmentservice.Department;
import org.example.departmentservice.DepartmentRepository;
import org.example.employeeservice.Employee;
import org.example.employeeservice.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeDepartmentAssignmentServiceImpl implements EmployeeDepartmentAssignmentService {
    private final EmployeeDepartmentAssignmentRepository bindingRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeDepartmentAssignmentMapper bindingMapper;

    public EmployeeDepartmentAssignmentServiceImpl(EmployeeDepartmentAssignmentRepository bindingRepository,
                                                   DepartmentRepository departmentRepository,
                                                   EmployeeRepository employeeRepository,
                                                   EmployeeDepartmentAssignmentMapper bindingMapper) {
        this.bindingRepository = bindingRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.bindingMapper = bindingMapper;
    }

    @Override
    public EmployeeDepartmentAssignmentDTO createBinding(EmployeeDepartmentAssignmentDTO bindingDTO) {
        Department department = departmentRepository.findById(bindingDTO.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Department", bindingDTO.getDepartment().getId()));
        Employee employee = employeeRepository.findById(bindingDTO.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", bindingDTO.getEmployee().getId()));

        EmployeeDepartmentAssignment binding = bindingMapper.toEntity(bindingDTO, department, employee);

        EmployeeDepartmentAssignment savedBinding = bindingRepository.save(binding);

        return bindingMapper.toDTO(savedBinding);
    }

    @Override
    public List<EmployeeDepartmentAssignmentDTO> getAllBindings() {
        return bindingRepository.findAll().stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDepartmentAssignmentDTO> getBindingsByIds(Long departmentId, Long employeeId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        List<EmployeeDepartmentAssignment> bindings = bindingRepository
                .findByDepartmentIdAndEmployeeId(departmentId, employeeId);

        return bindings.stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDepartmentAssignmentDTO getBindingById(Long bindingId) {
        EmployeeDepartmentAssignment binding = bindingRepository.findById(bindingId)
                .orElseThrow(() -> new EntityNotFoundException("Binding", bindingId));
        return bindingMapper.toDTO(binding);
    }

    @Override
    public EmployeeDepartmentAssignmentDTO updateBinding(Long bindingId, EmployeeDepartmentAssignmentDTO updatedBindingDTO) {
        EmployeeDepartmentAssignment existingBinding = bindingRepository.findById(bindingId)
                .orElseThrow(() -> new EntityNotFoundException("Binding", bindingId));

        Department department = departmentRepository.findById(updatedBindingDTO.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Department", updatedBindingDTO.getDepartment().getId()));
        Employee employee = employeeRepository.findById(updatedBindingDTO.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", updatedBindingDTO.getEmployee().getId()));

        // Обновление привязки
        existingBinding.setDepartment(department);
        existingBinding.setEmployee(employee);

        EmployeeDepartmentAssignment updatedBinding = bindingRepository.save(existingBinding);

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
        List<EmployeeDepartmentAssignment> bindings = bindingRepository
                .findByDepartmentIdAndEmployeeId(departmentId, employeeId);

        bindingRepository.deleteAll(bindings);
    }
}
