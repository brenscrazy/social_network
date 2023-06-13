package com.gleb.vinnikov.social_network.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;
    private String header;
    private String postText;
    @OneToOne
    @JoinColumn(name = "imageId")
    private Image image;

}
