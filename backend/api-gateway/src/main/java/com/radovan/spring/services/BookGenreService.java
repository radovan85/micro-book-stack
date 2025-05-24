package com.radovan.spring.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface BookGenreService {

    JsonNode getGenreById(Integer genreId);

    String addGenre(JsonNode genre);

    String updateGenre(JsonNode genre, Integer genreId);

    String deleteGenre(Integer genreId);

    List<JsonNode> listAll();
}
