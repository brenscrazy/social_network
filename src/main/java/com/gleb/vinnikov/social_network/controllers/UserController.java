package com.gleb.vinnikov.social_network.controllers;

import com.gleb.vinnikov.social_network.entities.User;
import com.gleb.vinnikov.social_network.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    @PostMapping(path = "/add")
    public String put(User user) {
        userRepo.save(user);
        return userRepo.findAll().toString();
    }

}
