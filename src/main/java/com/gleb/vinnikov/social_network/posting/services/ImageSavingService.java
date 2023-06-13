package com.gleb.vinnikov.social_network.posting.services;

import com.gleb.vinnikov.social_network.entities.Image;
import com.gleb.vinnikov.social_network.repos.ImageRepo;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.UUID;

import static com.gleb.vinnikov.social_network.utils.ExceptionUtils.retrieveErrorMessage;
import static com.gleb.vinnikov.social_network.utils.ExceptionUtils.throwIllegalArgumentException;

@Service
@RequiredArgsConstructor
public class ImageSavingService {

    private final Path imageSavingDirectory;
    private final ImageRepo imageRepo;

    public Image saveImage(MultipartFile image) {
        validate(image);
        String newFileName = generateFileName();
        Path savingFile = imageSavingDirectory.resolve(newFileName);
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, savingFile);
        } catch (IOException e) {
            throw new UncheckedIOException(retrieveErrorMessage("posting.image.saving.io.error"), e);
        }
        return imageRepo.save(Image.builder().imagePath(savingFile.toString()).build());
    }

    private void validate(MultipartFile image) {
        if (!Objects.equals(image.getContentType(), "image/jpeg")) {
            throwIllegalArgumentException("posting.image.saving.wrong.format");
        }
        if (image.isEmpty()) {
            throwIllegalArgumentException("posting.image.saving.empty.file");
        }
    }

    private String generateFileName() {
        return UUID.randomUUID() + ".jpg";
    }

}
