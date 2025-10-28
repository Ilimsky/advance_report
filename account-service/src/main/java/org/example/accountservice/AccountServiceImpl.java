package org.example.accountservice;

import org.example.common_utils.GenericServiceImpl;
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