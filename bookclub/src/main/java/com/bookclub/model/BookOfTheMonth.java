package com.bookclub.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

public class BookOfTheMonth {

    @Id
    private String id;

    private Integer month;

    @NotEmpty(message = "ISBN is a required field.")
    private String isbn;

    // Constructors
    public BookOfTheMonth() {
    }

    public BookOfTheMonth(Integer month, String isbn) {
        this.month = month;
        this.isbn = isbn;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Override toString()
    @Override
    public String toString() {
        return "BookOfTheMonth{id='" + id + "', month=" + month + ", isbn='" + isbn + "'}";
    }
}
