package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.UserDto;
import ru.practicum.explore.model.NewUserRequest;
import ru.practicum.explore.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Slf4j
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @RequestParam(required = false, defaultValue = "0")
                                  @PositiveOrZero Integer from,
                                  @RequestParam(required = false, defaultValue = "25")
                                  @Positive Integer size) {
        log.debug("Get users {} request received in controller {}", ids, this.getClass());
        List<UserDto> users = userService.getUsers(ids, from, size);
        log.debug("Get users {} request processed in controller {}", ids, this.getClass());
        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid NewUserRequest request) {
        log.debug("Create user request received in controller {}", this.getClass());
        UserDto createdUser = userService.createUser(request);
        log.debug("Create user request processed in controller {}", this.getClass());
        return createdUser;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        log.debug("Delete user request received in controller {}", this.getClass());
        userService.deleteUser(userId);
        log.debug("Delete user request processed in controller {}", this.getClass());
    }
}
