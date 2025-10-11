package org.example.revizor_backend.controller;

import org.example.revizor_backend.dto.RevizorDTO;
import org.example.revizor_backend.service.RevizorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/revizors")
@CrossOrigin(origins = "*")
public class RevizorController extends GenericController<RevizorDTO, Long> {


    @Autowired
    public RevizorController(RevizorService revizorService){
        super(revizorService);
    }
//    private final RevizorServiceImpl revizorServiceImpl;
//
//    @Autowired
//    public RevizorController(RevizorServiceImpl revizorServiceImpl) {
//        this.revizorServiceImpl = revizorServiceImpl;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<RevizorDTO>> getAllRevizors() {
//        List<RevizorDTO> revizorDTOs = revizorServiceImpl.getAllRevizors();
//        return ResponseEntity.ok(revizorDTOs);
//    }
//    // Создать новый департамент
//    @PostMapping
//    public ResponseEntity<RevizorDTO> createRevizor(@RequestBody RevizorDTO revizorDTO) {
//        RevizorDTO createRevizorDTO = revizorServiceImpl.createRevizor(revizorDTO);
//        return ResponseEntity.ok(createRevizorDTO);
//    }
//    // Получить департамент по ID
//    @GetMapping("/{id}")
//    public ResponseEntity<RevizorDTO> getRevizorById(@PathVariable Long id) {
//        Optional<RevizorDTO> revizorDTO = revizorServiceImpl.getRevizorById(id);
//        return revizorDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//    // Удалить департамент по ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<RevizorDTO> deleteRevizor(@PathVariable Long id) {
//        try {
//            revizorServiceImpl.deleteRevizor(id);
//            return ResponseEntity.noContent().build();
//        } catch (RevizorNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Обновить департамент по ID
//    @PutMapping("/{id}")
//    public ResponseEntity<RevizorDTO> updateRevizor(@PathVariable Long id, @RequestBody RevizorDTO revizorDTO) {
//        try{
//            RevizorDTO updateRevizorDTO = revizorServiceImpl.updateRevizor(id, revizorDTO);
//            return ResponseEntity.ok(updateRevizorDTO);
//        }catch(RevizorNotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//    }
}