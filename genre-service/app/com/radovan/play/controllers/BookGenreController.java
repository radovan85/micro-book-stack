package com.radovan.play.controllers;

import com.radovan.play.dto.BookGenreDto;
import com.radovan.play.exceptions.DataNotValidatedException;
import com.radovan.play.security.JwtAuthAction;
import com.radovan.play.security.RoleSecured;
import com.radovan.play.services.BookGenreService;
import jakarta.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;

@With(JwtAuthAction.class)
public class BookGenreController extends Controller {

    private BookGenreService genreService;
    private FormFactory formFactory;

    @Inject
    private void initialize(BookGenreService genreService, FormFactory formFactory) {
        this.genreService = genreService;
        this.formFactory = formFactory;
    }


    @RoleSecured({"ROLE_ADMIN"})
    public Result createGenre(Http.Request request){
        Form<BookGenreDto> form = formFactory.form(BookGenreDto.class).bindFromRequest(request);
        if (form.hasErrors()) {
            throw new DataNotValidatedException("Book genre data is not valid!");
        }
        BookGenreDto genreDto = form.get();
        BookGenreDto createdGenre = genreService.addGenre(genreDto);
        return ok("Book genre with id " + createdGenre.getGenreId() + " has been created!");
    }


    @RoleSecured({"ROLE_ADMIN"})
    public Result updateGenre(Http.Request request,Integer genreId){
        Form<BookGenreDto> form = formFactory.form(BookGenreDto.class).bindFromRequest(request);
        if (form.hasErrors()) {
            throw new DataNotValidatedException("Book genre data is not valid!");
        }
        BookGenreDto genreDto = form.get();
        BookGenreDto updatedGenre = genreService.updateGenre(genreDto,genreId);
        return ok("Book genre with id " + updatedGenre.getGenreId() + " has been updated!");
    }

    public Result getAllGenres(){
        return ok(Json.toJson(genreService.listAll()));
    }

    public Result getGenreDetails(Integer genreId){
        return ok(Json.toJson(genreService.getGenreById(genreId)));
    }

    @RoleSecured({"ROLE_ADMIN"})
    public Result deleteGenre(Http.Request request, Integer genreId){ // Dodali smo Request kao parametar
        genreService.deleteGenre(request, genreId); // Sada prosleđujemo request

        return ok(Json.toJson("The genre with id " + genreId + " has been permanently deleted!"));
    }

}
