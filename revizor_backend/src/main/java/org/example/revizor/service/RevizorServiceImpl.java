package org.example.revizor.service;

import org.example.revizor.dto.RevizorDTO;
import org.example.revizor.mapper.RevizorMapper;
import org.example.revizor.model.Revizor;
import org.example.revizor.repository.RevizorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevizorServiceImpl extends GenericServiceImpl<Revizor, RevizorDTO, Long> implements RevizorService {


    @Autowired
    public RevizorServiceImpl(RevizorRepository repository, RevizorMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void updateEntity(Revizor entity, RevizorDTO dto) {
        entity.setName(dto.getName());
    }

}