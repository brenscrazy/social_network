package com.gleb.vinnikov.social_network.auth.api;

import com.gleb.vinnikov.social_network.entities.Role;
import lombok.Data;

@Data
public class RegistrationRequest {

    private final String username;
    private final String email;
    private final String password;
    private final Role role;

}
