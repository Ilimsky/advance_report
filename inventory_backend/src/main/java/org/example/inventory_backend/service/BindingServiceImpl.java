package org.example.inventory_backend.service;

import org.example.inventory_backend.dto.BindingDTO;
import org.example.inventory_backend.exception.EntityNotFoundException;
import org.example.inventory_backend.mapper.BindingMapper;
import org.example.inventory_backend.model.Binding;
import org.example.inventory_backend.model.Department;
import org.example.inventory_backend.model.Employee;
import org.example.inventory_backend.repository.BindingRepository;
import org.example.inventory_backend.repository.DepartmentRepository;
import org.example.inventory_backend.repository.EmployeeRepository;
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
        // Проверка существования связанных сущностей
        Department department = departmentRepository.findById(bindingDTO.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Department", bindingDTO.getDepartment().getId()));
        Employee employee = employeeRepository.findById(bindingDTO.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", bindingDTO.getEmployee().getId()));

        // Создание привязки
        Binding binding = bindingMapper.toEntity(bindingDTO, department, employee);

        // Сохранение в базе данных
        Binding savedBinding = bindingRepository.save(binding);

        return bindingMapper.toDTO(savedBinding);
    }

    @Override
    public List<BindingDTO> getAllBindings() {
        // Получение всех привязок
        return bindingRepository.findAll().stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BindingDTO> getBindingsByIds(Long departmentId,  Long employeeId) {
        // Проверка существования связанных сущностей
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        // Поиск и возврат всех привязок
        List<Binding> bindings = bindingRepository
                .findByDepartmentIdAndEmployeeId(departmentId, employeeId);

        return bindings.stream()
                .map(bindingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BindingDTO getBindingById(Long bindingId) {
        // Получение привязки по ID
        Binding binding = bindingRepository.findById(bindingId)
                .orElseThrow(() -> new EntityNotFoundException("Binding", bindingId));
        return bindingMapper.toDTO(binding);
    }

    @Override
    public BindingDTO updateBinding(Long bindingId, BindingDTO updatedBindingDTO) {
        // Получение существующей привязки
        Binding existingBinding = bindingRepository.findById(bindingId)
                .orElseThrow(() -> new EntityNotFoundException("Binding", bindingId));

        // Проверка и обновление привязок
        Department department = departmentRepository.findById(updatedBindingDTO.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Department", updatedBindingDTO.getDepartment().getId()));
//        Job job = jobRepository.findById(updatedBindingDTO.getJob().getId())
//                .orElseThrow(() -> new EntityNotFoundException("Job", updatedBindingDTO.getJob().getId()));
        Employee employee = employeeRepository.findById(updatedBindingDTO.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", updatedBindingDTO.getEmployee().getId()));

        // Обновление привязки
        existingBinding.setDepartment(department);
//        existingBinding.setJob(job);
        existingBinding.setEmployee(employee);

        // Сохранение обновлённой привязки
        Binding updatedBinding = bindingRepository.save(existingBinding);

        return bindingMapper.toDTO(updatedBinding);
    }

    @Override
    public void deleteBinding(Long bindingId) {
        // Проверка на существование и удаление привязки по ID
        if (!bindingRepository.existsById(bindingId)) {
            throw new EntityNotFoundException("Binding", bindingId);
        }
        bindingRepository.deleteById(bindingId);
    }

    @Override
    public void deleteBindingsByIds(Long departmentId, Long employeeId) {
        // Проверка существования сущностей для удаления
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        // Удаление привязок
        List<Binding> bindings = bindingRepository
                .findByDepartmentIdAndEmployeeId(departmentId, employeeId);

        bindingRepository.deleteAll(bindings);
    }
}