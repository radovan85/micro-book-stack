package com.radovan.play.controllers;

import com.radovan.play.dto.BookImageDto;
import com.radovan.play.exceptions.FileUploadException;
import com.radovan.play.security.JwtAuthAction;
import com.radovan.play.services.BookImageService;
import jakarta.inject.Inject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;

import java.util.List;

@With(JwtAuthAction.class)
public class BookImageController extends Controller {

    private BookImageService imageService;

    @Inject
    private void initialize(BookImageService imageService) {
        this.imageService = imageService;
    }

    public Result storeImage(Http.Request request, Integer bookId) {
        Http.MultipartFormData<play.libs.Files.TemporaryFile> body = request.body().asMultipartFormData();
        Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile> filePart = body.getFile("file");

        if (filePart != null) {
            try {

                BookImageDto imageDto = imageService.addImage(request, filePart, bookId); // Sada prosleđujemo request

                return ok("Image for book with id " + bookId + " has been uploaded");
            } catch (FileUploadException e) {
                return badRequest("Failed to upload image: " + e.getMessage());
            }
        } else {
            return badRequest("Missing file");
        }
    }



    public Result getAllImages(){
        List<BookImageDto> allImages = imageService.listAll();
        return ok(Json.toJson(allImages));
    }
}
