package java.az.bankservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.az.bankservice.model.auth.AuthenticationRequest;
import java.az.bankservice.model.auth.AuthenticationResponseDto;
import java.az.bankservice.model.auth.ChangePasswordRequest;
import java.az.bankservice.model.auth.RegisterRequest;
import java.az.bankservice.service.AuthenticationService;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponseDto register(@RequestBody @Valid RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationResponseDto authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request, Principal connectedUser) {
        authenticationService.changePassword(request, connectedUser);
    }


}