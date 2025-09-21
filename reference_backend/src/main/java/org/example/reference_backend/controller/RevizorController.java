package org.example.reference_backend.controller;

import org.example.reference_backend.dto.RevizorDTO;
import org.example.reference_backend.service.RevizorService;
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
}