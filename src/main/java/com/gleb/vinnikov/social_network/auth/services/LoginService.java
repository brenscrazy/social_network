package com.gleb.vinnikov.social_network.auth.services;

import com.gleb.vinnikov.social_network.auth.api.LoginRequest;
import com.gleb.vinnikov.social_network.auth.api.LoginResponse;
import com.gleb.vinnikov.social_network.auth.api.RegistrationRequest;
import com.gleb.vinnikov.social_network.auth.jwt.JwtService;
import com.gleb.vinnikov.social_network.entities.User;
import com.gleb.vinnikov.social_network.repos.UserRepo;
import com.gleb.vinnikov.social_network.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Pattern emailPattern;
    private final Pattern usernamePattern;

    public LoginResponse registration(RegistrationRequest registrationRequest) {
        validateEmail(registrationRequest.getEmail());
        validateUsername(registrationRequest.getUsername());
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

    private void validateEmail(String email) {
        if (!emailPattern.matcher(email).matches()) {
            ExceptionUtils.throwIllegalArgumentException("registration.error.wrong.email.format", email);
        }
        if (userRepo.existsUserByEmail(email)) {
            ExceptionUtils.throwIllegalArgumentException("registration.error.email.is.taken", email);
        }
    }

    private void validateUsername(String username) {
        if (!usernamePattern.matcher(username).matches()) {
            ExceptionUtils.throwIllegalArgumentException("registration.error.wrong.username.format", username);
        }
        if (userRepo.existsUserByUsername(username)) {
            ExceptionUtils.throwIllegalArgumentException("registration.error.username.is.taken", username);
        }
    }

}
