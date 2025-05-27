package com.radovan.play.services;

import com.radovan.play.dto.BookGenreDto;
import play.mvc.Http;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface BookGenreService {

    BookGenreDto getGenreById(Integer genreId);
    BookGenreDto addGenre(BookGenreDto genreDto);
    BookGenreDto updateGenre(BookGenreDto genreDto,Integer genreId);
    void deleteGenre(Http.Request request, Integer genreId);
    List<BookGenreDto> listAll();
}
