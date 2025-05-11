package org.example.inventory_backend.controller;

import org.example.inventory_backend.dto.DepartmentDTO;
import org.example.inventory_backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController extends GenericController<DepartmentDTO, Long> {

    @Autowired
    public DepartmentController(DepartmentService departmentService){
        super(departmentService);
    }{}


//    private final DepartmentServiceImpl departmentServiceImpl;
//
//    @Autowired
//    public DepartmentController(DepartmentServiceImpl departmentServiceImpl) {
//        this.departmentServiceImpl = departmentServiceImpl;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
//        List<DepartmentDTO> departmentDTOs = departmentServiceImpl.getAllDepartments();
//        return ResponseEntity.ok(departmentDTOs);
//    }
//
//    // Создать новый департамент
//    @PostMapping
//    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
//        DepartmentDTO createDepartmentDTO = departmentServiceImpl.createDepartment(departmentDTO);
//        return ResponseEntity.ok(createDepartmentDTO);
//    }
//
//    // Получить департамент по ID
//    @GetMapping("/{id}")
//    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
//        Optional<DepartmentDTO> departmentDTO = departmentServiceImpl.getDepartmentById(id);
//        return departmentDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Удалить департамент по ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable Long id) {
//        try {
//            departmentServiceImpl.deleteDepartment(id);
//            return ResponseEntity.noContent().build();
//        } catch (DepartmentNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Обновить департамент по ID
//    @PutMapping("/{id}")
//    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
//        try{
//            DepartmentDTO updateDepartmentDTO = departmentServiceImpl.updateDepartment(id, departmentDTO);
//            return ResponseEntity.ok(updateDepartmentDTO);
//        }catch(DepartmentNotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//    }
}