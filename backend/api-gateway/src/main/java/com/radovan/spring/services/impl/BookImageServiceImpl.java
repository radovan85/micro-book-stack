package com.radovan.spring.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.spring.converter.DeserializeConverter;
import com.radovan.spring.services.BookImageService;
import com.radovan.spring.utils.MultipartFileResource;
import com.radovan.spring.utils.ServiceUrlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BookImageServiceImpl implements BookImageService {

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
    public String addImage(MultipartFile file, Integer bookId) {
        String url = urlProvider.getImageServiceUrl() + "/api/images/" + bookId;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new MultipartFileResource(file)); // koristi MultipartFileResource za ispravan format

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    String.class);

            return response.getBody();
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert file to resource", e);
        }
    }

    @Override
    public List<JsonNode> listAll() {
        // TODO Auto-generated method stub
        String url = urlProvider.getImageServiceUrl() + "/api/images";
        return deserializeConverter.getJsonNodeList(url);
    }
}
