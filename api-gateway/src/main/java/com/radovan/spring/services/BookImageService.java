package com.radovan.spring.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookImageService {

    String addImage(MultipartFile file, Integer bookId);

    List<JsonNode> listAll();
}
