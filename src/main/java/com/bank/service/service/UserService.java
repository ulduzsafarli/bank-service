package com.bank.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bank.service.model.users.UserCreateDto;
import com.bank.service.model.users.UserResponse;
import com.bank.service.model.users.UserUpdateDto;
import com.bank.service.model.users.profile.UserProfileDto;
import com.bank.service.model.users.profile.UserProfileFilterDto;

public interface UserService {

    Page<UserProfileDto> findByFilter(UserProfileFilterDto filter, Pageable pageRequest);

    UserResponse getById(Long id);

    UserResponse update(Long id, UserUpdateDto userCreateDto);

    void delete(Long id);

    UserResponse create(UserCreateDto userCreateDto);


}
