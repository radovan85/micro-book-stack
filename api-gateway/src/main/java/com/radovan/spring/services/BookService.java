package com.radovan.spring.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface BookService {

    String addBook(JsonNode book);

    JsonNode getBookById(Integer bookId);

    String updateBook(Integer bookId, JsonNode book);

    String deleteBook(Integer bookId);

    List<JsonNode> listAll();

    List<JsonNode> listAllByGenreId(Integer genreId);

    String deleteAllByGenreId(Integer genreId);

}
