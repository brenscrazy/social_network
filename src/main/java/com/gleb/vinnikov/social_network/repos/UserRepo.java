package com.gleb.vinnikov.social_network.repos;

import com.gleb.vinnikov.social_network.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsernameOrEmail(String username, String email);

    Optional<User> findFirstByUsername(String username);

    Optional<User> findFirstByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

}
