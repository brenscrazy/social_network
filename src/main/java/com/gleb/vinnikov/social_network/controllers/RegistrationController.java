package com.gleb.vinnikov.social_network.controllers;

import com.gleb.vinnikov.social_network.entities.User;
import com.gleb.vinnikov.social_network.repos.UserRepo;
import com.gleb.vinnikov.social_network.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@RestController
public class RegistrationController {

    RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestParam(required = true) String username,
                                               @RequestParam(required = true) String password,
                                               @RequestParam(required = true) String email) {
//        User newUser = new User();
//        newUser.setUsername(username);
//        newUser.setEmail(email);
        return ResponseEntity.ok().body(registrationService.registration(username, password, email));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleConstraintViolation(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
