package dev.zwazel.springintro.security.auth;

import dev.zwazel.springintro.security.auth.payload.AuthenticationRequest;
import dev.zwazel.springintro.security.auth.payload.AuthenticationResponse;
import dev.zwazel.springintro.security.auth.payload.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}