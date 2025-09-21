package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.AccountDTO;
import org.example.reference_backend.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements GenericMapper<Account, AccountDTO> {

    public AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountDTO(
                account.getId(),
                account.getName()
        );
    }

    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }

        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setName(accountDTO.getName());

        return account;
    }
}