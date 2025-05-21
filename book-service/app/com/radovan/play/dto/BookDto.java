package com.radovan.play.dto;

import play.data.validation.Constraints;

import java.io.Serializable;

public class BookDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer bookId;

    @Constraints.Required
    @Constraints.MinLength(3)
    @Constraints.MaxLength(75)
    private String title;

    @Constraints.Required
    @Constraints.MinLength(3)
    @Constraints.MaxLength(50)
    private String author;

    @Constraints.Required
    @Constraints.MinLength(3)
    @Constraints.MaxLength(50)
    private String description;

    private Integer imageId;

    @Constraints.Required
    private Integer genreId;

    @Constraints.Required
    private Double price;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public @Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(75) String getTitle() {
        return title;
    }

    public void setTitle(@Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(75) String title) {
        this.title = title;
    }

    public @Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(50) String getAuthor() {
        return author;
    }

    public void setAuthor(@Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(50) String author) {
        this.author = author;
    }

    public @Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(50) String getDescription() {
        return description;
    }

    public void setDescription(@Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(50) String description) {
        this.description = description;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public @Constraints.Required Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(@Constraints.Required Integer genreId) {
        this.genreId = genreId;
    }

    public @Constraints.Required Double getPrice() {
        return price;
    }

    public void setPrice(@Constraints.Required Double price) {
        this.price = price;
    }
}
