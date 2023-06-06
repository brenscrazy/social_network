package com.gleb.vinnikov.social_network.auth.api;

import com.gleb.vinnikov.social_network.auth.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/registration")
    public ResponseEntity<LoginResponse> registration(
            RegistrationRequest request) {
        return ResponseEntity.ok().body(loginService.registration(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            LoginRequest request) {
        return ResponseEntity.ok().body(loginService.login(request));
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class, BadCredentialsException.class})
    public ResponseEntity<String> handleBadCredentialsException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Bad credentials. Please check your username and password");
    }

}
