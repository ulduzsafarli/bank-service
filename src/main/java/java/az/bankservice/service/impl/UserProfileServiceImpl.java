package java.az.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.az.bankservice.entities.UserProfile;
import java.az.bankservice.mapper.UserProfileMapper;
import java.az.bankservice.model.users.profile.UserProfileDto;
import java.az.bankservice.model.users.profile.UserProfileFilterDto;
import java.az.bankservice.repository.UserProfileRepository;
import java.az.bankservice.service.UserProfileService;
import java.az.bankservice.util.specifications.UserProfileSpecifications;

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
