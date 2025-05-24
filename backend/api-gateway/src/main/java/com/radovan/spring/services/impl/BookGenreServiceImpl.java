package com.radovan.spring.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.spring.converter.DeserializeConverter;
import com.radovan.spring.services.BookGenreService;
import com.radovan.spring.utils.ServiceUrlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookGenreServiceImpl implements BookGenreService {

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
    public JsonNode getGenreById(Integer genreId) {
        String url = urlProvider.getGenreServiceUrl() + "/api/genres/" + genreId;
        ResponseEntity<JsonNode> response = deserializeConverter.getJsonNodeResponse(url);
        return response.getBody();
    }

    @Override
    public String addGenre(JsonNode genre) {
        String url = urlProvider.getGenreServiceUrl() + "/api/genres";
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(genre,deserializeConverter.getJsonHeaders());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }

    @Override
    public String updateGenre(JsonNode genre, Integer genreId) {
        String url = urlProvider.getGenreServiceUrl() + "/api/genres/" + genreId;
        HttpEntity<JsonNode> requestEntity = new HttpEntity<JsonNode>(genre,deserializeConverter.getJsonHeaders());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
                String.class);
        return response.getBody();
    }

    @Override
    public String deleteGenre(Integer genreId) {
        String url = urlProvider.getGenreServiceUrl() + "/api/genres/" + genreId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }

    @Override
    public List<JsonNode> listAll() {
        String url = urlProvider.getGenreServiceUrl() + "/api/genres";
        return deserializeConverter.getJsonNodeList(url);
    }
}
