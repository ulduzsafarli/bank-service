package com.bank.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bank.service.model.users.profile.UserProfileDto;
import com.bank.service.model.users.profile.UserProfileFilterDto;

public interface UserProfileService {
    Page<UserProfileDto> findByFilter(UserProfileFilterDto filterDto, Pageable pageRequest);
    boolean existsByPhoneNumber(String phoneNumber);
}
