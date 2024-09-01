package java.az.bankservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.az.bankservice.model.users.UserCreateDto;
import java.az.bankservice.model.users.UserResponse;
import java.az.bankservice.model.users.UserUpdateDto;
import java.az.bankservice.model.users.profile.UserProfileDto;
import java.az.bankservice.model.users.profile.UserProfileFilterDto;
import java.az.bankservice.service.UserService;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/search")
    public Page<UserProfileDto> getByFilter(UserProfileFilterDto filter, Pageable pageable) {
        return userService.findByFilter(filter, pageable);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserCreateDto user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
