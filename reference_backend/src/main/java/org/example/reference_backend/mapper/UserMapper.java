package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.UserDTO;
import org.example.reference_backend.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
