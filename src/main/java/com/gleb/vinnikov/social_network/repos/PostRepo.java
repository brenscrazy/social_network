package com.gleb.vinnikov.social_network.repos;

import com.gleb.vinnikov.social_network.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepo extends JpaRepository<Post, UUID> {
}
