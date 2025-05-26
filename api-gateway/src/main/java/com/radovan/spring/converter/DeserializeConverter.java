package com.radovan.spring.converter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Component
public class DeserializeConverter {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper; // Jackson ObjectMapper

    public Map<String, Object> deserializeJson(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("*** GREŠKA PRI DESERIALIZACIJI JSON-A!", e);
        }
    }

    public ResponseEntity<JsonNode> getJsonNodeResponse(String url){
        return restTemplate.getForEntity(url, JsonNode.class);
    }

    public List<JsonNode> getJsonNodeList(String url) {
        HttpEntity<Void> entity = new HttpEntity<>(null);

        ResponseEntity<List<JsonNode>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<JsonNode>>() {});

        return response.getBody();
    }

    public HttpHeaders getJsonHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
