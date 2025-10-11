package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.AccountDTO;
import org.example.reference_backend.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper extends GenericMapper<Account, AccountDTO> {

    @Override
    AccountDTO toDTO(Account account);

    @Override
    Account toEntity(AccountDTO accountDTO);
}