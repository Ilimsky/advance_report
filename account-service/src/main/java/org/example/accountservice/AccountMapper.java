package org.example.accountservice;

import org.example.common_utils.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper extends GenericMapper<Account, AccountDTO> {
    @Override
    AccountDTO toDTO(Account account);
    @Override
    Account toEntity(AccountDTO accountDTO);
}