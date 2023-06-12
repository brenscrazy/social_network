package com.gleb.vinnikov.social_network.repos;

import com.gleb.vinnikov.social_network.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepo extends JpaRepository<Image, UUID> {

    public boolean existsImageByImagePath(String imagePath);

}
