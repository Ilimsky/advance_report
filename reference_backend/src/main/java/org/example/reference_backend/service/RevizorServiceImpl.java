package org.example.reference_backend.service;

import org.example.reference_backend.dto.RevizorDTO;
import org.example.reference_backend.mapper.RevizorMapper;
import org.example.reference_backend.model.Revizor;
import org.example.reference_backend.repository.RevizorRepository;
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