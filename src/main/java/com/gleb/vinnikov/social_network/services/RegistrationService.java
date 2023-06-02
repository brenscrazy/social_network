package com.gleb.vinnikov.social_network.services;

import com.gleb.vinnikov.social_network.entities.User;
import com.gleb.vinnikov.social_network.repos.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final Pattern regexp = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public RegistrationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/registration")
    public String registration(String username,
                               String password,
                               String email) {
        validateBeforeRegistration(username, email);
        userRepo.save(new User(username, password, email));
        return "ololo";
    }

    private void validateBeforeRegistration(String username, String email) {
        if (!regexp.matcher(email).matches()) {
            throw new IllegalArgumentException("");
        }
        Optional<User> optionalUser = userRepo.findFirstByUsernameOrEmail(username, email);
        optionalUser.map(user -> {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            if (Objects.equals(user.getEmail(), email)) {
                throw new IllegalArgumentException(
                        MessageFormat.format(bundle.getString("registration.error.email.is.taken"), email));
            } else {
                throw new IllegalArgumentException(
                        MessageFormat.format(bundle.getString("registration.error.username.is.taken"), username));
            }
        });
    }

}
