package com.radovan.play.controllers;

import com.radovan.play.dto.BookDto;
import com.radovan.play.exceptions.DataNotValidatedException;
import com.radovan.play.security.JwtAuthAction;
import com.radovan.play.security.RoleSecured;
import com.radovan.play.services.BookService;
import jakarta.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;

@With(JwtAuthAction.class)
public class BookController extends Controller {

    private BookService bookService;
    private FormFactory formFactory;

    @Inject
    private void initialize(BookService bookService, FormFactory formFactory) {
        this.bookService = bookService;
        this.formFactory = formFactory;
    }

    @RoleSecured({"ROLE_ADMIN"})
    public Result createBook(Http.Request request){
        Form<BookDto> form = formFactory.form(BookDto.class).bindFromRequest(request);
        if (form.hasErrors()) {
            throw new DataNotValidatedException("Book data is not valid!");
        }

        BookDto bookDto = form.get();
        BookDto createdBook = bookService.addBook(request, bookDto); // Sada prosleđujemo request

        return ok("Book with id " + createdBook.getBookId() + " has been created!");
    }


    public Result getBookDetails(Integer bookId){
        BookDto book = bookService.getBookById(bookId);
        return ok(Json.toJson(book));
    }

    public Result getAllBooks(){
        return ok(Json.toJson(bookService.listAll()));
    }

    public Result getAllBooksByGenre(Integer genreId){
        return ok(Json.toJson(bookService.listAllByGenreId(genreId)));
    }

    @RoleSecured({"ROLE_ADMIN"})
    public Result deleteBook(Integer bookId){
        bookService.deleteBook(bookId);
        return ok(Json.toJson("The book with id " + bookId + " has been permanently deleted!"));
    }

    @RoleSecured({"ROLE_ADMIN"})
    public Result updateBook(Http.Request request,Integer bookId){
        Form<BookDto> form = formFactory.form(BookDto.class).bindFromRequest(request);
        if (form.hasErrors()) {
            throw new DataNotValidatedException("Book data is not valid!");
        }
        BookDto bookDto = form.get();
        BookDto updatedBook = bookService.updateBook(request,bookId,bookDto);
        return ok("Book with id " + updatedBook.getBookId() + " has been updated!");
    }

    @RoleSecured({"ROLE_ADMIN"})
    public Result deleteBooks(Http.Request request, Integer genreId){ // Dodali smo Request kao parametar
        bookService.deleteAllByGenreId(request, genreId); // Sada prosleđujemo request

        return ok(Json.toJson("All books with genre id " + genreId + " have been permanently deleted!"));
    }

}
