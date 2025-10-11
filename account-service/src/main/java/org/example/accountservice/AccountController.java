package org.example.accountservice;

import org.example.common_utils.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController extends GenericController<AccountDTO, Long> {

    @Autowired
    public AccountController(AccountService accountService) {
        super(accountService);
    }
}
