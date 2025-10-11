package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.RevizorDTO;
import org.example.reference_backend.model.Revizor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface RevizorMapper extends GenericMapper<Revizor, RevizorDTO>{

    @Override
    RevizorDTO toDTO(Revizor revizor);

    @Override
    Revizor toEntity(RevizorDTO revizorDTO);
}