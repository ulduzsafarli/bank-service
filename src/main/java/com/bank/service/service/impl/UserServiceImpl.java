package com.bank.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.service.entities.User;
import com.bank.service.enumeration.auth.Role;
import com.bank.service.exception.custom.DuplicateDataException;
import com.bank.service.exception.custom.NotFoundException;
import com.bank.service.mapper.UserMapper;
import com.bank.service.model.users.UserCreateDto;
import com.bank.service.model.users.UserResponse;
import com.bank.service.model.users.UserUpdateDto;
import com.bank.service.model.users.profile.UserProfileDto;
import com.bank.service.model.users.profile.UserProfileFilterDto;
import com.bank.service.repository.UserRepository;
import com.bank.service.service.util.UserUtilService;
import com.bank.service.service.UserProfileService;
import com.bank.service.service.UserService;
import com.bank.service.util.GenerateRandom;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserUtilService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserProfileService userProfileService;
    private final UserMapper userMapper;

    private static final String NOT_FOUND_WITH_ID = "User with ID %d not found";

    @Override
    public Page<UserProfileDto> findByFilter(UserProfileFilterDto filter, Pageable pageRequest) {
        log.info("Searching users by filter: {}", filter);
        return userProfileService.findByFilter(filter, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        log.info("Retrieving user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_WITH_ID, id)));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserUpdateDto userUpdateDto) {
        log.info("Updating user with ID {} to: {}", id, userUpdateDto);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_WITH_ID, id)));
        user = userMapper.updateEntityFromRequest(userUpdateDto, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting user by ID: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserResponse create(UserCreateDto userCreateDto) {
        log.info("Adding new user: {}", userCreateDto);
        validateNewUserData(userCreateDto);
        User user = userMapper.toEntity(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setRole(userCreateDto.getRoles().isEmpty() ? Role.USER : userCreateDto.getRoles().iterator().next());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void createCif(Long userId) {
        log.info("Creating cif for user with ID: {}", userId);
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_WITH_ID, userId)));
        if (user.getCif() == null) {
            user.setCif(GenerateRandom.generateCif());
            userRepository.save(user);
        } else log.info("The user has CIF");
    }

    private void validateNewUserData(UserCreateDto userCreateDto) {
        Optional<User> existingUserByEmail = userRepository.findByEmail(userCreateDto.getEmail());
        if (existingUserByEmail.isPresent())
            throw new DuplicateDataException("User with email " + userCreateDto.getEmail() + " already exists");

        if (userProfileService.existsByPhoneNumber(userCreateDto.getUserProfile().getPhoneNumber()))
            throw new DuplicateDataException("User with phone number " + userCreateDto.getUserProfile().getPhoneNumber() + " already exists");
    }

}
