package com.bank.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.bank.service.model.auth.AuthenticationRequest;
import com.bank.service.model.auth.AuthenticationResponseDto;
import com.bank.service.model.auth.ChangePasswordRequest;
import com.bank.service.model.auth.RegisterRequest;
import com.bank.service.service.AuthenticationService;
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