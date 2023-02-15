package ru.practicum.explore.mappers;

import org.mapstruct.Mapper;
import ru.practicum.explore.dto.UserDto;
import ru.practicum.explore.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);
}
