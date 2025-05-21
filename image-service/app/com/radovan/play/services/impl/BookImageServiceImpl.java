package com.radovan.play.services.impl;

import com.radovan.play.clients.BookServiceClient;
import com.radovan.play.converter.ImageConverter;
import com.radovan.play.dto.BookImageDto;
import com.radovan.play.entity.BookImageEntity;
import com.radovan.play.exceptions.FileUploadException;
import com.radovan.play.exceptions.InstanceUndefinedException;
import com.radovan.play.repositories.BookImageRepository;
import com.radovan.play.services.BookImageService;
import com.radovan.play.utils.FileValidator;
import com.radovan.play.utils.NatsSubscriber;
import com.radovan.play.utils.NatsUtils;
import io.nats.client.Connection;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import play.mvc.Http;

import java.nio.file.Files;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Singleton
public class BookImageServiceImpl  implements BookImageService {

    private BookImageRepository imageRepository;
    private ImageConverter imageConverter;
    private FileValidator fileValidator;
    private BookServiceClient bookServiceClient;
    private NatsUtils natsUtils;
    private Provider<NatsSubscriber> natsSubscriberProvider;

    @Inject
    private void initialize(BookImageRepository imageRepository, ImageConverter imageConverter, FileValidator fileValidator, BookServiceClient bookServiceClient, NatsUtils natsUtils, Provider<NatsSubscriber> natsSubscriberProvider) {
        this.imageRepository = imageRepository;
        this.imageConverter = imageConverter;
        this.fileValidator = fileValidator;
        this.bookServiceClient = bookServiceClient;
        this.natsUtils = natsUtils;
        this.natsSubscriberProvider = natsSubscriberProvider;
    }

    @Override
    public void deleteImage(Integer imageId) {
        imageRepository.deleteById(imageId);
    }

    @Override
    public BookImageDto addImage(Http.Request request, Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile> file, Integer bookId) {
        fileValidator.validateFile(file);

        if (!bookExists(request, bookId)) { // Prosleđujemo request da se token pravilno prenese
            throw new InstanceUndefinedException("Book with this ID does not exist!");
        }

        // Obriši postojeću sliku ako postoji
        imageRepository.findByBookId(bookId)
                .ifPresent(existingImage -> deleteImage(existingImage.getId()));

        try {
            // Kreiraj novu sliku
            BookImageDto image = new BookImageDto();
            image.setBookId(bookId);
            image.setName(Objects.requireNonNull(file.getFilename()));
            image.setContentType(file.getContentType());
            image.setSize(file.getFileSize());
            image.setData(Files.readAllBytes(file.getRef().path()));

            // Sačuvaj u bazu
            BookImageEntity storedImage = imageRepository.save(imageConverter.dtoToEntity(image));

            // Definiši subject i poruku pre slanja
            String subject = "book.image.uploaded";
            String message = String.format("{\"bookId\":%d,\"imageId\":%d}", bookId, storedImage.getId());

            // Pošalji poruku preko Singleton NATS konekcije
            Connection nc = natsUtils.getConnection();
            nc.publish(subject, message.getBytes());
            nc.flush(Duration.ofSeconds(2));
            System.out.println("*** Message has been sent via NATS: " + message);

            return imageConverter.entityToDto(storedImage);
        } catch (Exception e) {
            throw new FileUploadException("Greška pri uploadu slike: " + e.getMessage());
        }
    }


    @Override
    public List<BookImageDto> listAll() {
        List<BookImageEntity> allImages = imageRepository.findAll();
        return allImages.stream().map(imageConverter::entityToDto).collect(Collectors.toList());
    }

    private boolean bookExists(Http.Request request, Integer bookId) {
        // Proverava postojanje knjige preko eksternog book-service mikroservisa
        return bookServiceClient
                .bookExists(request, bookId) // Sada prosleđujemo request!
                .toCompletableFuture()
                .join();
    }

}