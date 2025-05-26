package com.radovan.play.services;

import com.radovan.play.dto.BookDto;
import play.mvc.Http;

import java.util.List;

public interface BookService {


    BookDto addBook(Http.Request request, BookDto bookDto);

    BookDto getBookById(Integer bookId);

    BookDto updateBook(Http.Request request, Integer bookId, BookDto bookDto);

    void deleteBook(Integer bookId);
    List<BookDto> listAll();
    List<BookDto> listAllByGenreId(Integer genreId);

    void deleteAllByGenreId(Http.Request request, Integer genreId);

    void addImage(Integer bookId, Integer imageId);
}
