package org.example.inventory_backend.service;

import org.example.inventory_backend.dto.DepartmentDTO;
import org.example.inventory_backend.mapper.DepartmentMapper;
import org.example.inventory_backend.model.Department;
import org.example.inventory_backend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends GenericServiceImpl<Department, DepartmentDTO, Long> implements DepartmentService {


    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository, DepartmentMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void updateEntity(Department entity, DepartmentDTO dto) {
        entity.setName(dto.getName());
    }

//    private final DepartmentRepository departmentRepository;
//    private final DepartmentMapper departmentMapper;
//
//    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
//        this.departmentRepository = departmentRepository;
//        this.departmentMapper = departmentMapper;
//    }
//
//    @Override
//    public List<DepartmentDTO> getAllDepartments() {
//        return departmentRepository.findAll()
//                .stream()
//                .map(departmentMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
//        Department department = departmentMapper.toEntity(departmentDTO);
//        Department savedDepartment = departmentRepository.save(department);
//        return departmentMapper.toDTO(savedDepartment);
//    }
//
//    @Override
//    public Optional<DepartmentDTO> getDepartmentById(Long id) {
//        return departmentRepository.findById(id)
//                .map(departmentMapper::toDTO);
//    }
//
//    @Override
//    public void deleteDepartment(Long id) {
//        departmentRepository.deleteById(id);
//    }
//
//    @Override
//    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
//        Department department = departmentRepository.findById(id)
//                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
//
//        department.setName(departmentDTO.getName());
//
//        Department updatedDepartment = departmentRepository.save(department);
//        return departmentMapper.toDTO(updatedDepartment);
//    }
}