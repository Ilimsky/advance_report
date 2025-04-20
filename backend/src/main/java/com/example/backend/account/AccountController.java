package com.example.backend.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountServiceImpl accountServiceImpl;

    @Autowired
    public AccountController(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accountDTOs = accountServiceImpl.getAllAccounts();
        return ResponseEntity.ok(accountDTOs);
    }

    // Создать новый департамент
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO createAccountDTO = accountServiceImpl.createAccount(accountDTO);
        return ResponseEntity.ok(createAccountDTO);
    }

    // Получить департамент по ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        Optional<AccountDTO> accountDTO = accountServiceImpl.getAccountById(id);
        if (accountDTO.isPresent()) {
            return ResponseEntity.ok(accountDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить департамент по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable Long id) {
        try {
            accountServiceImpl.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Обновить департамент по ID
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        try{
            AccountDTO updateAccountDTO = accountServiceImpl.updateAccount(id, accountDTO);
            return ResponseEntity.ok(updateAccountDTO);
        }catch(AccountNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}