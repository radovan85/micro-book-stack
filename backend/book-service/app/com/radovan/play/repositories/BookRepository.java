package com.radovan.play.repositories;

import com.radovan.play.entity.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<BookEntity> findById(Integer bookId);

    void deleteById(Integer bookId);

    BookEntity save(BookEntity bookEntity);

    List<BookEntity> findAll();

    List<BookEntity> findAllByGenre(Integer genreId);

    void deleteAllByGenreId(Integer genreId);
}
