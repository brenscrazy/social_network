package com.gleb.vinnikov.social_network.posting.api;

import com.gleb.vinnikov.social_network.entities.Post;
import com.gleb.vinnikov.social_network.posting.dtos.PostDto;
import com.gleb.vinnikov.social_network.posting.services.PostingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "posts")
public class PostingController {

    private final PostingService postingService;
    private final ModelMapper modelMapper;


    @PostMapping(
            value = "/post-in",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostingResponse> postIn(
            Principal principal,
            @Valid @RequestBody PostingRequest request) {
        Post post = postingService.postIn(principal.getName(), request);
        return ResponseEntity.ok().body(new PostingResponse(post.getId()));
    }

    @GetMapping(
            value = "/{postId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> getPost(
            Principal principal,
            @PathVariable String postId) {
        Optional<Post> post = postingService.getPost(UUID.fromString(postId));
        return post.map(this::convertToDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    private PostDto convertToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

}
