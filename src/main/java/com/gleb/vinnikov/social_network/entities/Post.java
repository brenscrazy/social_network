package com.gleb.vinnikov.social_network.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;
    @OneToMany
    @JoinColumn(name = "authorId")
    private User author;
    private String header;
    private String postText;
    private UUID imageId;

    public void setId(UUID id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public UUID getId() {
        return id;
    }
}
