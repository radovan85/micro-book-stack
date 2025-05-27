package com.radovan.spring.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.spring.services.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/genres")
@CrossOrigin(value="*")
public class GatewayGenreController {

    @Autowired
    private BookGenreService genreService;

    @PostMapping
    public ResponseEntity<String> createGenre(@RequestBody JsonNode genre) {
        return new ResponseEntity<>(genreService.addGenre(genre), HttpStatus.OK);
    }

    @PutMapping(value = "/{genreId}")
    public ResponseEntity<String> updateGenre(@RequestBody JsonNode genre, @PathVariable("genreId") Integer genreId) {
        return new ResponseEntity<>(genreService.updateGenre(genre, genreId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<JsonNode>> getAllGenres() {
        return new ResponseEntity<>(genreService.listAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{genreId}")
    public ResponseEntity<JsonNode> getGenreDetails(@PathVariable("genreId") Integer genreId) {
        return new ResponseEntity<>(genreService.getGenreById(genreId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{genreId}")
    public ResponseEntity<String> deleteGenre(@PathVariable("genreId") Integer genreId) {
        return new ResponseEntity<>(genreService.deleteGenre(genreId), HttpStatus.OK);
    }
}