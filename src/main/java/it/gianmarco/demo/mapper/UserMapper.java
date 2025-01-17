package it.gianmarco.demo.mapper;

import it.gianmarco.demo.entity.User;
import it.gianmarco.demo.entity.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
