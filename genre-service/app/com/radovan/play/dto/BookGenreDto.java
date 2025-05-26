package com.radovan.play.dto;

import play.data.validation.Constraints;

import java.io.Serializable;

public class BookGenreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer genreId;

    @Constraints.Required
    @Constraints.MinLength(3)
    @Constraints.MaxLength(40)
    private String name;

    @Constraints.Required
    @Constraints.MinLength(3)
    @Constraints.MaxLength(100)
    private String description;

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public @Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(40) String getName() {
        return name;
    }

    public void setName(@Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(40) String name) {
        this.name = name;
    }

    public @Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(100) String getDescription() {
        return description;
    }

    public void setDescription(@Constraints.Required @Constraints.MinLength(3) @Constraints.MaxLength(100) String description) {
        this.description = description;
    }
}
