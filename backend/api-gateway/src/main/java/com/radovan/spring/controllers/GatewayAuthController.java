package com.radovan.spring.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/auth")
@CrossOrigin(value="*")
public class GatewayAuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<JsonNode> createAuthenticationToken(@RequestBody JsonNode authRequest){
        return new ResponseEntity<>(userService.authenticateUser(authRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<JsonNode>> getAllUsers() {
        return new ResponseEntity<>(userService.listAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<JsonNode> getCurrentUser(){
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }

    @PutMapping(value="/suspend/{id}")
    public ResponseEntity<String> suspendUser (@PathVariable("id") Integer userId){
        return new ResponseEntity<>(userService.suspendUser(userId),HttpStatus.OK);
    }


    @PutMapping(value="/reactivate/{id}")
    public ResponseEntity<String> reactivateUser (@PathVariable("id") Integer userId){
        return new ResponseEntity<>(userService.reactivateUser(userId),HttpStatus.OK);
    }

    @DeleteMapping(value="/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @PostMapping(value="/register")
    public ResponseEntity<String> register(@RequestBody JsonNode user){
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }
}
