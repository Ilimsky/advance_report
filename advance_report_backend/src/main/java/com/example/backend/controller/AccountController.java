package com.example.backend.controller;

import com.example.backend.dto.AccountDTO;
import com.example.backend.dto.DepartmentDTO;
import com.example.backend.service.AccountService;
import com.example.backend.service.AccountServiceImpl;
import com.example.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController extends GenericController<AccountDTO, Long> {

    @Autowired
    public AccountController(AccountService accountService){
        super(accountService);
    }{}

}