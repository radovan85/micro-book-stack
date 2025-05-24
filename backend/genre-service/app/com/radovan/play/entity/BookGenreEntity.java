package com.radovan.play.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="genres")
public class BookGenreEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer genreId;

    @Column(nullable = false,length = 40,unique = true)
    private String name;

    @Column(nullable = false,length = 100)
    private String description;

    public BookGenreEntity() {
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
