package dev.zwazel.springintro.security.auth;

import dev.zwazel.springintro.security.auth.payload.AuthenticationRequest;
import dev.zwazel.springintro.security.auth.payload.AuthenticationResponse;
import dev.zwazel.springintro.security.auth.payload.RegisterRequest;
import dev.zwazel.springintro.security.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.register(request);
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(authenticationResponse.getAccessToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(authenticationResponse.getAccessToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(authenticationResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie jwtCookie = jwtService.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }
}