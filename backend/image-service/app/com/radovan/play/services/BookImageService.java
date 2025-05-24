package com.radovan.play.services;

import com.radovan.play.dto.BookImageDto;
import play.mvc.Http;

import java.util.List;

public interface BookImageService {

    void deleteImage(Integer imageId);

    BookImageDto addImage(Http.Request request, Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile> file, Integer bookId);

    List<BookImageDto> listAll();
}