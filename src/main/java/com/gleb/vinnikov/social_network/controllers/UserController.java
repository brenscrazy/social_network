package com.gleb.vinnikov.social_network.controllers;

import com.gleb.vinnikov.social_network.entities.User;
import com.gleb.vinnikov.social_network.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    @PostMapping(path = "/add")
    public String put(@RequestParam String nickName) {
        User user = new User();
        user.setNickName(nickName);
        userRepo.save(user);
        return userRepo.findAll().toString();
    }

}
