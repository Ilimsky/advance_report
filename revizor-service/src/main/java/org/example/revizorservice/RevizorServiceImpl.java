package org.example.revizorservice;

import org.example.common_utils.GenericServiceImpl;
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