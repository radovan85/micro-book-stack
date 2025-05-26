package com.radovan.spring.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.spring.converter.DeserializeConverter;
import com.radovan.spring.services.UserService;
import com.radovan.spring.utils.ServiceUrlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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
    public List<JsonNode> listAll() {
        String url = urlProvider.getAuthServiceUrl() + "/api/auth/users";
        return deserializeConverter.getJsonNodeList(url);
    }

    @Override
    public JsonNode getCurrentUser() {
        String url = urlProvider.getAuthServiceUrl() + "/api/auth/me";
        System.out.println("Current user url: " + url);
        return deserializeConverter.getJsonNodeResponse(url).getBody();
    }

    @Override
    public JsonNode authenticateUser(JsonNode authRequest) {
        String url = urlProvider.getAuthServiceUrl() + "/api/auth/login";
        System.out.println("Login URL:" + url);
        HttpEntity<JsonNode> requestEntity = new HttpEntity<JsonNode>(authRequest, deserializeConverter.getJsonHeaders());
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class);
        System.out.println("Response: " + response.getBody());
        return response.getBody();
    }

    @Override
    public String suspendUser(Integer userId) {
        String url = urlProvider.getAuthServiceUrl() + "/api/auth/suspend/" + userId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        return response.getBody();
    }

    @Override
    public String reactivateUser(Integer userId) {
        String url = urlProvider.getAuthServiceUrl() + "/api/auth/reactivate/" + userId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        return response.getBody();
    }

    @Override
    public String addUser(JsonNode user) {
        String url = urlProvider.getAuthServiceUrl() + "/api/auth/register";
        System.out.println("Register URL:" + url);
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(user, deserializeConverter.getJsonHeaders());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println("Response: " + response.getBody());
        return response.getBody();
    }

    @Override
    public String deleteUser(Integer userId) {
        String url = urlProvider.getAuthServiceUrl() + "/api/auth/delete/" + userId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }


}
