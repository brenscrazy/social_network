package com.gleb.vinnikov.social_network.posting.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Validated
public class PostingController {

    @PostMapping(value = "/post", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> getName(
            Principal principal,
            @Valid @RequestBody PostingRequest request) {
        return ResponseEntity.ok().body(principal.getName());
    }

}
