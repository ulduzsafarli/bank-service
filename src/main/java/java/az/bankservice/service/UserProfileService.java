package java.az.bankservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.az.bankservice.model.users.profile.UserProfileDto;
import java.az.bankservice.model.users.profile.UserProfileFilterDto;

public interface UserProfileService {
    Page<UserProfileDto> findByFilter(UserProfileFilterDto filterDto, Pageable pageRequest);
    boolean existsByPhoneNumber(String phoneNumber);
}
