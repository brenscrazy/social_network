package com.gleb.vinnikov.social_network.entities;

import jakarta.persistence.*;
import lombok.*;

import java.nio.file.Path;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String imagePath;



}
