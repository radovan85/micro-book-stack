package com.radovan.spring.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.spring.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/books")
@CrossOrigin(value="*")
public class GatewayBookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody JsonNode book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.OK);
    }

    @GetMapping(value = "/{bookId}")
    public ResponseEntity<JsonNode> getBookDetails(@PathVariable("bookId") Integer bookId) {
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<JsonNode>> getAllBooks() {
        return new ResponseEntity<>(bookService.listAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/selectedBooks/{genreId}")
    public ResponseEntity<List<JsonNode>> getAllBooksByGenre(@PathVariable("genreId") Integer genreId) {
        return new ResponseEntity<>(bookService.listAllByGenreId(genreId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") Integer bookId) {
        return new ResponseEntity<>(bookService.deleteBook(bookId), HttpStatus.OK);
    }

    @PutMapping(value = "/{bookId}")
    public ResponseEntity<String> updateBook(@RequestBody JsonNode book, @PathVariable("bookId") Integer bookId) {
        return new ResponseEntity<>(bookService.updateBook(bookId, book), HttpStatus.OK);
    }
}