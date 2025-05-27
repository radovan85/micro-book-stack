package com.radovan.play.dto;

import play.data.validation.Constraints;

import java.io.Serializable;

public class BookImageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String contentType;

    private Long size;

    private byte[] data;

    @Constraints.Required
    private Integer bookId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public @Constraints.Required Integer getBookId() {
        return bookId;
    }

    public void setBookId(@Constraints.Required Integer bookId) {
        this.bookId = bookId;
    }
}