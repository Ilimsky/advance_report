package com.example.backend.account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<AccountDTO> getAllAccounts();

    AccountDTO createAccount(AccountDTO accountDTO);

    Optional<AccountDTO> getAccountById(Long id);

    void deleteAccount(Long id);

    AccountDTO updateAccount(Long id, AccountDTO accountDTO);
}