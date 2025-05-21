package com.radovan.spring.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.spring.converter.DeserializeConverter;
import com.radovan.spring.services.BookService;
import com.radovan.spring.utils.ServiceUrlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private DeserializeConverter deserializeConverter;
    private RestTemplate restTemplate;
    private ServiceUrlProvider urlProvider;

    @Autowired
    private void initialize(DeserializeConverter deserializeConverter, RestTemplate restTemplate, ServiceUrlProvider urlProvider) {
        this.deserializeConverter = deserializeConverter;
        this.restTemplate = restTemplate;
        this.urlProvider = urlProvider;
    }

    @Override
    public String addBook(JsonNode book) {
        String url = urlProvider.getBookServiceUrl() + "/api/books";
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(book,deserializeConverter.getJsonHeaders());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }

    @Override
    public JsonNode getBookById(Integer bookId) {
        String url = urlProvider.getBookServiceUrl() + "/api/books/" + bookId;
        ResponseEntity<JsonNode> response = deserializeConverter.getJsonNodeResponse(url);
        return response.getBody();
    }

    @Override
    public String updateBook(Integer bookId, JsonNode book) {
        String url = urlProvider.getBookServiceUrl() + "/api/books/" + bookId;
        HttpEntity<JsonNode> requestEntity = new HttpEntity<JsonNode>(book,deserializeConverter.getJsonHeaders());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
                String.class);
        return response.getBody();
    }

    @Override
    public String deleteBook(Integer bookId) {
        String url = urlProvider.getBookServiceUrl() + "/api/books/" + bookId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }

    @Override
    public List<JsonNode> listAll() {
        String url = urlProvider.getBookServiceUrl() + "/api/books";
        return deserializeConverter.getJsonNodeList(url);
    }

    @Override
    public List<JsonNode> listAllByGenreId(Integer genreId) {
        String url = urlProvider.getBookServiceUrl() + "/api/books/selectedBooks/" + genreId;
        return deserializeConverter.getJsonNodeList(url);
    }

    @Override
    public String deleteAllByGenreId(Integer genreId) {
        String url = urlProvider.getBookServiceUrl() + "/api/books/deleteBooks/" + genreId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }





}
