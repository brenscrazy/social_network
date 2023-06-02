package com.gleb.vinnikov.social_network.repos;

import com.gleb.vinnikov.social_network.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {

    public Optional<User> findFirstByUsernameOrEmail(String username, String email);

}
