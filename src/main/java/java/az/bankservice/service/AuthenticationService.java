package java.az.bankservice.service;


import java.az.bankservice.model.auth.AuthenticationRequest;
import java.az.bankservice.model.auth.AuthenticationResponseDto;
import java.az.bankservice.model.auth.ChangePasswordRequest;
import java.az.bankservice.model.auth.RegisterRequest;
import java.security.Principal;

public interface AuthenticationService {
    AuthenticationResponseDto register(RegisterRequest request);

    AuthenticationResponseDto authenticate(AuthenticationRequest request);

    void changePassword(ChangePasswordRequest request, Principal connectedUser);


}
