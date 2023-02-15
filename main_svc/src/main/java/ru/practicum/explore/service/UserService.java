package ru.practicum.explore.service;

import ru.practicum.explore.dto.UserDto;
import ru.practicum.explore.model.NewUserRequest;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto createUser(NewUserRequest request);

    void deleteUser(Long userId);
}
