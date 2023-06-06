package com.gleb.vinnikov.social_network.auth.services;

import com.gleb.vinnikov.social_network.auth.api.LoginRequest;
import com.gleb.vinnikov.social_network.auth.api.LoginResponse;
import com.gleb.vinnikov.social_network.auth.api.RegistrationRequest;
import com.gleb.vinnikov.social_network.auth.jwt.JwtService;
import com.gleb.vinnikov.social_network.entities.User;
import com.gleb.vinnikov.social_network.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.module.Configuration;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Pattern emailRegexp;
    // TODO: 06.06.2023 add regexps

    public LoginResponse registration(RegistrationRequest registrationRequest) {
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .role(registrationRequest.getRole())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();
        User saved = userRepo.save(user);
        String accessToken = jwtService.generateAccessToken(saved);
        return new LoginResponse(accessToken);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        User user = userRepo.findFirstByUsername(request.getUsername())
                .orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponse(accessToken);
    }

}
