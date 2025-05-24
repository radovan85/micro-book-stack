package com.radovan.spring.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.spring.services.BookImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value="/api/images")
@CrossOrigin(value="*")
public class GatewayImageController {

    @Autowired
    private BookImageService imageService;

    @PostMapping(value="/{bookId}")
    public ResponseEntity<String> storeImage(@RequestPart("file") MultipartFile file, @PathVariable("bookId") Integer bookId){
        return new ResponseEntity<>(imageService.addImage(file,bookId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<JsonNode>> getAllImages(){
        return new ResponseEntity<>(imageService.listAll(),HttpStatus.OK);
    }
}
