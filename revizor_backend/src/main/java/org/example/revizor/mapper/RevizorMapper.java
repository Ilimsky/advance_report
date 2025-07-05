package org.example.revizor.mapper;

import org.example.revizor.dto.DepartmentDTO;
import org.example.revizor.dto.RevizorDTO;
import org.example.revizor.model.Department;
import org.example.revizor.model.Revizor;
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