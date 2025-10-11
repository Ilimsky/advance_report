package com.example.backend.service;

import com.example.backend.dto.AccountDTO;
import com.example.backend.dto.AccountDTO;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.mapper.AccountMapper;
import com.example.backend.mapper.AccountMapper;
import com.example.backend.model.Account;
import com.example.backend.model.Account;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, AccountDTO, Long> implements AccountService {


    @Autowired
    public AccountServiceImpl(AccountRepository repository, AccountMapper mapper) {
        super(repository, mapper);
    }

//    @Override
//    protected void updateEntity(Account entity, AccountDTO dto) {
//        entity.setName(dto.getName());
//    }

}