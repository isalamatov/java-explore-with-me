package ru.practicum.explore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.dto.UserDto;
import ru.practicum.explore.mappers.UserMapper;
import ru.practicum.explore.model.NewUserRequest;
import ru.practicum.explore.model.User;
import ru.practicum.explore.repository.UserRepository;
import ru.practicum.explore.service.UserService;

import java.util.List;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        log.debug("Get users request received in service {}", this.getClass());
        Pageable page = PageRequest.of(from / size, size);
        List<User> users = userRepository.findAllByIdIn(ids, page);
        List<UserDto> userDtos = userMapper.toDto(users);
        log.debug("Get users request processed in service {}", this.getClass());
        return userDtos;
    }

    @Override
    public UserDto createUser(NewUserRequest request) {
        log.debug("Create user request received in service {}", this.getClass());
        User user = new User().setName(request.getName()).setEmail(request.getEmail());
        userRepository.save(user);
        UserDto userDto = userMapper.toDto(user);
        log.debug("Create user request processed in service {}", this.getClass());
        return userDto;
    }

    @Override
    public void deleteUser(Long userId) {
        log.debug("Delete user request with id {} received in service {}", userId, this.getClass());
        userRepository.deleteById(userId);
        log.debug("Delete user request with id {} processed in service {}", userId, this.getClass());
    }
}
