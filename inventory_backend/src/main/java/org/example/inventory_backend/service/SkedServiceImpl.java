package org.example.inventory_backend.service;

import com.github.dockerjava.api.exception.ConflictException;
import org.example.inventory_backend.dto.SkedDTO;
import org.example.inventory_backend.exception.EntityNotFoundException;
import org.example.inventory_backend.mapper.SkedMapper;
import org.example.inventory_backend.model.*;
import org.springframework.stereotype.Service;
import org.example.inventory_backend.repository.SkedRepository;
import org.example.inventory_backend.repository.DepartmentRepository;
import org.example.inventory_backend.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkedServiceImpl implements SkedService {

    private final SkedRepository skedRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final SkedMapper skedMapper;

    public SkedServiceImpl(SkedRepository skedRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, SkedMapper skedMapper) {
        this.skedRepository = skedRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.skedMapper = skedMapper;
    }

    @Override
    public SkedDTO createSked(SkedDTO skedDTO) {
        Department department = departmentRepository.findById(skedDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", skedDTO.getDepartmentId()));
        Employee employee = employeeRepository.findById(skedDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", skedDTO.getDepartmentId()));

        List<Sked> skeds = skedRepository.findByDepartmentById(skedDTO.getDepartmentId());
        int nextSkedNumber = skeds.size() + 1;

        String formattedSkedNumber = String.format("%06d", nextSkedNumber);

        boolean numberExists = skedRepository.existsBySkedNumberAndDepartment_Id(formattedSkedNumber, skedDTO.getDepartmentId());
        if (numberExists) {
            throw new ConflictException("Инвентарный номер " + formattedSkedNumber + " уже существует в этом отделе");
        }

        if (nextSkedNumber > 999999) {
            throw new IllegalStateException("Достигнут максимальный номер (999999) для отдела");
        }

        Sked sked = skedMapper.toEntity(skedDTO, department, employee);
        sked.setSkedNumber(Integer.parseInt(formattedSkedNumber));

        Sked savedSked = skedRepository.save(sked);
        return skedMapper.toDTO(savedSked);
    }

    @Override
    public List<SkedDTO> getAllSkeds() {
        return skedRepository.findAll().stream()
                .map(skedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SkedDTO> getSkedsByIds(Long departmentId, Long employeeId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        return skedRepository.findByDepartmentById(departmentId).stream()
                .map(skedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SkedDTO> getSkedsByDepartmentId(Long departmentId) {
        return skedRepository.findByDepartmentById(departmentId)
                .stream()
                .map(skedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SkedDTO getSkedById(Long skedId) {
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));
        return skedMapper.toDTO(sked);
    }

    @Override
    public SkedDTO updateSked(Long skedId, SkedDTO updatedSkedDTO) {
        Sked existingSked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        Department department = departmentRepository.findById(updatedSkedDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", updatedSkedDTO.getDepartmentId()));
        Employee employee = employeeRepository.findById(updatedSkedDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", updatedSkedDTO.getEmployeeId()));

        // Обновление базовых полей
        existingSked.setDepartment(department);
        existingSked.setEmployee(employee);
        existingSked.setDepartmentIdentifier(updatedSkedDTO.getDepartmentIdentifier());
        existingSked.setEmployeeIdentifier(updatedSkedDTO.getEmployeeIdentifier());

        existingSked.setAssetCategory(updatedSkedDTO.getAssetCategory());
        existingSked.setDateReceived(updatedSkedDTO.getDateReceived());
        existingSked.setSkedNumber(updatedSkedDTO.getSkedNumber());
        existingSked.setItemName(updatedSkedDTO.getItemName());
        existingSked.setSerialNumber(updatedSkedDTO.getSerialNumber());
        existingSked.setCount(updatedSkedDTO.getCount());
        existingSked.setMeasure(updatedSkedDTO.getMeasure());
        existingSked.setPrice(updatedSkedDTO.getPrice());
        existingSked.setPlace(updatedSkedDTO.getPlace());
        existingSked.setComments(updatedSkedDTO.getComments());

        Sked updatedSked = skedRepository.save(existingSked);
        return skedMapper.toDTO(updatedSked);
    }



    @Override
    public void deleteSked(Long skedId) {
        if (!skedRepository.existsById(skedId)) {
            throw new EntityNotFoundException("Sked", skedId);
        }
        skedRepository.deleteById(skedId);
    }

    @Override
    public void deleteSkedsByIds(Long departmentId, Long employeeId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        List<Sked> skeds = skedRepository.findByDepartmentById(departmentId);
        skedRepository.deleteAll(skeds);
    }
}