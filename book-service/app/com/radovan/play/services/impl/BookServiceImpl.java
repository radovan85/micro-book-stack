package com.radovan.play.services.impl;

import com.radovan.play.converter.BookConverter;
import com.radovan.play.dto.BookDto;
import com.radovan.play.entity.BookEntity;
import com.radovan.play.exceptions.InstanceUndefinedException;
import com.radovan.play.repositories.BookRepository;
import com.radovan.play.services.BookService;
import com.radovan.play.clients.GenreServiceClient;
import com.radovan.play.utils.NatsSubscriber;
import com.radovan.play.utils.NatsUtils;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import play.mvc.Http;

import io.nats.client.Connection;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Singleton
public class BookServiceImpl implements BookService {

    private final Provider<NatsSubscriber> natsSubscriberProvider;
    private final Provider<BookConverter> bookConverterProvider;
    private final Provider<BookRepository> bookRepositoryProvider;
    private final Provider<GenreServiceClient> genreServiceClientProvider;
    private final Provider<NatsUtils> natsUtilsProvider;

    @Inject
    public BookServiceImpl(Provider<NatsSubscriber> natsSubscriberProvider, Provider<BookConverter> bookConverterProvider, Provider<BookRepository> bookRepositoryProvider, Provider<GenreServiceClient> genreServiceClientProvider, Provider<NatsUtils> natsUtilsProvider) {
        this.natsSubscriberProvider = natsSubscriberProvider;
        this.bookConverterProvider = bookConverterProvider;
        this.bookRepositoryProvider = bookRepositoryProvider;
        this.genreServiceClientProvider = genreServiceClientProvider;
        this.natsUtilsProvider = natsUtilsProvider;
    }


    private boolean genreExists(Http.Request request, Integer genreId) {
        // Proverava postojanje žanra preko eksternog genre-service mikroservisa
        return genreServiceClientProvider
                .get()
                .genreExists(request, genreId) // Sada prosleđujemo request!
                .toCompletableFuture()
                .join(); // Blokira dok ne stigne odgovor
    }







    @Override
    public BookDto addBook(Http.Request request, BookDto bookDto) { // Dodali smo Request kao parametar
        if (!genreExists(request, bookDto.getGenreId())) { // Sada prosleđujemo request
            throw new InstanceUndefinedException("Genre with this ID does not exist!");
        }

        BookEntity storedBook = bookRepositoryProvider.get().save(bookConverterProvider.get().dtoToEntity(bookDto));
        return bookConverterProvider.get().entityToDto(storedBook);
    }


    @Override
    public BookDto getBookById(Integer bookId) {
        BookEntity bookEntity = bookRepositoryProvider.get().findById(bookId)
                .orElseThrow(() -> new InstanceUndefinedException("The book has not been found"));
        return bookConverterProvider.get().entityToDto(bookEntity);
    }

    @Override
    public BookDto updateBook(Http.Request request, Integer bookId, BookDto bookDto) { // Dodali smo Request kao parametar
        getBookById(bookId); // Proverava da li knjiga postoji

        if (!genreExists(request, bookDto.getGenreId())) { // Sada prosleđujemo request
            throw new InstanceUndefinedException("Genre with this ID does not exist!");
        }

        bookDto.setBookId(bookId);
        BookEntity updatedBook = bookRepositoryProvider.get().save(bookConverterProvider.get().dtoToEntity(bookDto));
        return bookConverterProvider.get().entityToDto(updatedBook);
    }


    @Override
    public void deleteBook(Integer bookId) {
        BookDto book = getBookById(bookId); // Proveri da li knjiga postoji
        Optional<Integer> imageIdOptional = Optional.ofNullable(book.getImageId());

        bookRepositoryProvider.get().deleteById(bookId);

        // Ako knjiga ima povezanu sliku, šaljemo poruku ka image-service
        imageIdOptional.ifPresent(imageId -> {
            String subject = "book.image.deleted";
            String message = String.format("{\"imageId\":%d}", imageId); // Samo imageId je bitan

            Connection nc = natsUtilsProvider.get().getConnection();
            nc.publish(subject, message.getBytes());
            try {
                nc.flush(Duration.ofSeconds(2));
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    public List<BookDto> listAll() {
        List<BookEntity> allBooks = bookRepositoryProvider.get().findAll();
        return allBooks.stream().map(bookConverterProvider.get()::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> listAllByGenreId(Integer genreId) {
        List<BookEntity> allBooks = bookRepositoryProvider.get().findAllByGenre(genreId);
        return allBooks.stream().map(bookConverterProvider.get()::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAllByGenreId(Http.Request request, Integer genreId) { // Dodali smo Request kao parametar
        if (!genreExists(request, genreId)) { // Sada prosleđujemo request
            throw new InstanceUndefinedException("Genre with this ID does not exist!");
        }

        bookRepositoryProvider.get().deleteAllByGenreId(genreId);
    }


    @Override
    public void addImage(Integer bookId,Integer imageId) {
        BookDto book = getBookById(bookId);
        book.setImageId(imageId);
        bookRepositoryProvider.get().save(bookConverterProvider.get().dtoToEntity(book));
    }
}