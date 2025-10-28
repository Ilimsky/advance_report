package org.example.reference_backend.service;

import org.example.reference_backend.dto.AccountDTO;
import org.example.reference_backend.mapper.AccountMapper;
import org.example.reference_backend.model.Account;
import org.example.reference_backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, AccountDTO, Long> implements AccountService {

    @Autowired
    public AccountServiceImpl(AccountRepository repository, AccountMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void updateEntity(Account entity, AccountDTO dto) {
        entity.setName(dto.getName());
    }

}