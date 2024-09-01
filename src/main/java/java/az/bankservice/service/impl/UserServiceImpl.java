package java.az.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.az.bankservice.entities.User;
import java.az.bankservice.enumeration.auth.Role;
import java.az.bankservice.exception.custom.DuplicateDataException;
import java.az.bankservice.exception.custom.NotFoundException;
import java.az.bankservice.mapper.UserMapper;
import java.az.bankservice.model.users.UserCreateDto;
import java.az.bankservice.model.users.UserResponse;
import java.az.bankservice.model.users.UserUpdateDto;
import java.az.bankservice.model.users.profile.UserProfileDto;
import java.az.bankservice.model.users.profile.UserProfileFilterDto;
import java.az.bankservice.repository.UserRepository;
import java.az.bankservice.service.util.UserUtilService;
import java.az.bankservice.service.UserProfileService;
import java.az.bankservice.service.UserService;
import java.az.bankservice.util.GenerateRandom;
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
