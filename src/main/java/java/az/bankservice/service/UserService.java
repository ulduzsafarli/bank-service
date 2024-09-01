package java.az.bankservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.az.bankservice.model.users.UserCreateDto;
import java.az.bankservice.model.users.UserResponse;
import java.az.bankservice.model.users.UserUpdateDto;
import java.az.bankservice.model.users.profile.UserProfileDto;
import java.az.bankservice.model.users.profile.UserProfileFilterDto;

public interface UserService {

    Page<UserProfileDto> findByFilter(UserProfileFilterDto filter, Pageable pageRequest);

    UserResponse getById(Long id);

    UserResponse update(Long id, UserUpdateDto userCreateDto);

    void delete(Long id);

    UserResponse create(UserCreateDto userCreateDto);


}
