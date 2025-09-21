package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.RevizorDTO;
import org.example.reference_backend.model.Revizor;
import org.springframework.stereotype.Component;

@Component
public class RevizorMapper implements GenericMapper<Revizor, RevizorDTO>{

    public RevizorDTO toDTO(Revizor revizor) {
        if (revizor == null) {
            return null;
        }

        return new RevizorDTO(
                revizor.getId(),
                revizor.getName()
        );
    }
    public Revizor toEntity(RevizorDTO revizorDTO) {
        if (revizorDTO == null) {
            return null;
        }

        Revizor revizor = new Revizor();
        revizor.setId(revizorDTO.getId());
        revizor.setName(revizorDTO.getName());

        return revizor;
    }
}