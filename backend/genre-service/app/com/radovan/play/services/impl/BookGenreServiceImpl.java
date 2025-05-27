package com.radovan.play.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.play.clients.BookServiceClient;
import com.radovan.play.converter.BookGenreConverter;
import com.radovan.play.dto.BookGenreDto;
import com.radovan.play.entity.BookGenreEntity;
import com.radovan.play.exceptions.ExistingInstanceException;
import com.radovan.play.exceptions.InstanceUndefinedException;
import com.radovan.play.repositories.BookGenreRepository;
import com.radovan.play.services.BookGenreService;
import com.radovan.play.utils.NatsUtils;
import io.nats.client.Connection;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import play.mvc.Http;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class BookGenreServiceImpl implements BookGenreService {

    private Provider<BookGenreRepository> genreRepositoryProvider;
    private Provider<BookGenreConverter> genreConverterProvider;
    private Provider<BookServiceClient> bookServiceClientProvider;
    private Provider<NatsUtils> natsUtilsProvider;

    @Inject
    private void initialize(Provider<BookGenreConverter> genreConverterProvider, Provider<BookGenreRepository> genreRepositoryProvider, Provider<BookServiceClient> bookServiceClientProvider, Provider<NatsUtils> natsUtilsProvider) {
        this.genreConverterProvider = genreConverterProvider;
        this.genreRepositoryProvider = genreRepositoryProvider;
        this.bookServiceClientProvider = bookServiceClientProvider;
        this.natsUtilsProvider = natsUtilsProvider;
    }

    @Override
    public BookGenreDto getGenreById(Integer genreId) {
        BookGenreEntity genreEntity = genreRepositoryProvider.get().findById(genreId)
                .orElseThrow(() -> new InstanceUndefinedException("The genre has not been found"));
        return genreConverterProvider.get().entityToDto(genreEntity);
    }


    @Override
    public BookGenreDto addGenre(BookGenreDto genreDto) {
        Optional<BookGenreEntity> genreOptional = genreRepositoryProvider.get().findByName(genreDto.getName());
        genreOptional.ifPresent(value -> {
            throw new ExistingInstanceException("This genre already exists!");
        });
        BookGenreEntity storedGenre = genreRepositoryProvider.get().save(genreConverterProvider.get().dtoToEntity(genreDto));
        return genreConverterProvider.get().entityToDto(storedGenre);
    }

    @Override
    public BookGenreDto updateGenre(BookGenreDto genreDto, Integer genreId) {
        BookGenreDto currentGenre = getGenreById(genreId);
        Optional<BookGenreEntity> genreOptional = genreRepositoryProvider.get().findByName(genreDto.getName());
        genreOptional.ifPresent(value -> {
            if (!Objects.equals(value.getGenreId(), genreId)) {
                throw new ExistingInstanceException("This genre already exists!");
            }
        });

        genreDto.setGenreId(genreId);
        BookGenreEntity updatedGenre = genreRepositoryProvider.get().save(genreConverterProvider.get().dtoToEntity(genreDto));
        return genreConverterProvider.get().entityToDto(updatedGenre);
    }




    @Override
    public void deleteGenre(Http.Request request, Integer genreId) {
        getGenreById(genreId); // Proverava da li žanr postoji

        try {

            // **Dohvati sve knjige tog žanra u JSON formatu**
            JsonNode booksJson = bookServiceClientProvider.get().getAllBooksByGenre(request, genreId);

            // **Iteriraj kroz JSON i šalji poruke za brisanje slika SAMO ako `imageId` nije `null` ili `0`**
            for (JsonNode bookNode : booksJson) {
                if (bookNode.has("imageId") && !bookNode.get("imageId").isNull() && bookNode.get("imageId").asInt() > 0) {
                    int imageId = bookNode.get("imageId").asInt();

                    String subject = "book.image.deleted";
                    String message = String.format("{\"imageId\":%d}", imageId);

                    Connection nc = natsUtilsProvider.get().getConnection();
                    nc.publish(subject, message.getBytes());
                    nc.flush(Duration.ofSeconds(2));
                    System.out.println("*** Image deletion event sent to image-service: " + message);
                }
            }

            // **Tek sada brišemo sve knjige**
            bookServiceClientProvider.get().deleteBooksByGenre(request, genreId);

            // **Na kraju brišemo žanr**
            genreRepositoryProvider.get().deleteById(genreId);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Osiguraj da se nit pravilno prekine
            System.err.println("*** Deletion process interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("*** ERROR DELETING GENRE: " + e.getMessage());
            e.printStackTrace();
        }
    }





    @Override
    public List<BookGenreDto> listAll() {
        List<BookGenreEntity> allGenres = genreRepositoryProvider.get().findAll();
        return allGenres.stream().map(genreConverterProvider.get()::entityToDto).collect(Collectors.toList());
    }
}