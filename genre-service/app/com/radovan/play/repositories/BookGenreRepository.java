package com.radovan.play.repositories;

import com.radovan.play.entity.BookGenreEntity;

import java.util.List;
import java.util.Optional;

public interface BookGenreRepository {

    Optional<BookGenreEntity> findById(Integer genreId);
    BookGenreEntity save(BookGenreEntity genreEntity);
    List<BookGenreEntity> findAll();
    void deleteById(Integer genreId);
    Optional<BookGenreEntity> findByName(String name);
}
