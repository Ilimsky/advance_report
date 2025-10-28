package org.example.revizorservice;

import org.example.common_utils.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RevizorMapper extends GenericMapper<Revizor, RevizorDTO> {

    @Override
    RevizorDTO toDTO(Revizor revizor);

    @Override
    Revizor toEntity(RevizorDTO revizorDTO);
}