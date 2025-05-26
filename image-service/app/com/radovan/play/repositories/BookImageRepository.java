package com.radovan.play.repositories;

import com.radovan.play.entity.BookImageEntity;

import java.util.List;
import java.util.Optional;

public interface BookImageRepository {

    BookImageEntity save(BookImageEntity imageEntity);

    Optional<BookImageEntity> findByBookId(Integer bookId);

    List<BookImageEntity> findAll();

    void deleteById(Integer imageId);

    Optional<BookImageEntity> findById(Integer imageId);
}
