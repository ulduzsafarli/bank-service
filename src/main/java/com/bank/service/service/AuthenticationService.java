package com.bank.service.service;


import com.bank.service.model.auth.AuthenticationRequest;
import com.bank.service.model.auth.AuthenticationResponseDto;
import com.bank.service.model.auth.ChangePasswordRequest;
import com.bank.service.model.auth.RegisterRequest;
import java.security.Principal;

public interface AuthenticationService {
    AuthenticationResponseDto register(RegisterRequest request);

    AuthenticationResponseDto authenticate(AuthenticationRequest request);

    void changePassword(ChangePasswordRequest request, Principal connectedUser);


}
