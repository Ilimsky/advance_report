package org.example.reference_backend.controller;

import org.example.reference_backend.dto.AccountDTO;
import org.example.reference_backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController extends GenericController<AccountDTO, Long> {

    @Autowired
    public AccountController(AccountService accountService){
        super(accountService);
    }{}

}