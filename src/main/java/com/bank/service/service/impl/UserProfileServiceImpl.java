package com.bank.service.service.impl;

import com.bank.service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bank.service.entities.UserProfile;
import com.bank.service.mapper.UserProfileMapper;
import com.bank.service.model.users.profile.UserProfileDto;
import com.bank.service.model.users.profile.UserProfileFilterDto;
import com.bank.service.repository.UserProfileRepository;
import com.bank.service.util.specifications.UserProfileSpecifications;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileMapper userProfileMapper;
    private final UserProfileRepository userProfileRepository;

    @Override
    public Page<UserProfileDto> findByFilter(UserProfileFilterDto filterDto, Pageable pageRequest) {
        Specification<UserProfile> userProfileSpecification = UserProfileSpecifications.getUserProfileSpecification(filterDto);
        Page<UserProfile> userProfileEntity = userProfileRepository.findAll(userProfileSpecification, pageRequest);
        log.info("Successfully found users");
        return userProfileEntity.map(userProfileMapper::toDto);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userProfileRepository.existsByPhoneNumber(phoneNumber);
    }


}
