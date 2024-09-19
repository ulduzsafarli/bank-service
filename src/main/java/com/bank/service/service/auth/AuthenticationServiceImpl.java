package com.bank.service.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.service.entities.User;
import com.bank.service.entities.UserProfile;
import com.bank.service.enumeration.auth.Role;
import com.bank.service.exception.custom.DuplicateDataException;
import com.bank.service.exception.custom.NotFoundException;
import com.bank.service.model.auth.AuthenticationRequest;
import com.bank.service.model.auth.AuthenticationResponseDto;
import com.bank.service.model.auth.ChangePasswordRequest;
import com.bank.service.model.auth.RegisterRequest;
import com.bank.service.repository.UserRepository;
import com.bank.service.service.AuthenticationService;
import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthenticationResponseDto register(RegisterRequest request) {
        log.info("Registering user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateDataException("User with email address already exists: " + request.getEmail());
        }

        UserProfile userProfile = UserProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthDate(request.getBirthDate())
                .phoneNumber(request.getPhoneNumber())
                .build();

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .cif(null)
                .role(request.getRoles().isEmpty() ? Role.USER : request.getRoles().iterator().next())
                .userProfile(userProfile)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        log.info("User registered successfully: {}", request.getEmail());

        return new AuthenticationResponseDto(jwtToken);
    }

    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequest request) {
        log.info("Authenticating user: {}", request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("User with email " + request.email() + " not found"));
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            String jwtToken = jwtService.generateToken(user);
            log.info("User authenticated successfully: {}", request.email());
            return new AuthenticationResponseDto(jwtToken);
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        log.info("Changing the password for user: {}", connectedUser.getName());

        User user = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(() -> new NotFoundException("User with email " + connectedUser.getName() + " not found"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new IllegalArgumentException("Passwords are not the same");
        }
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())){
            throw new IllegalArgumentException("The same passwords");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        log.info("Changed the password for user: {} successfully", user.getEmail());
    }
}
