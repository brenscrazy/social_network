package com.gleb.vinnikov.social_network.auth.api;

import lombok.Data;

@Data
public class LoginRequest {

    private final String username;
    private final String password;

}
