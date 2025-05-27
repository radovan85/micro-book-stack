package com.radovan.spring.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface UserService {

    List<JsonNode> listAll();

    JsonNode getCurrentUser();

    JsonNode authenticateUser(JsonNode authRequest);

    String suspendUser(Integer userId);

    String reactivateUser(Integer userId);

    String addUser(JsonNode user);

    String deleteUser(Integer userId);


}
