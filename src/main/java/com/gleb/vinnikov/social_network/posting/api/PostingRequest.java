package com.gleb.vinnikov.social_network.posting.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
public class PostingRequest {

    @NotNull(message = "header field is missing")
    private final String header;
    @NotNull(message = "postText field is missing")
    private final String postText;
    private final MultipartFile image;

}
