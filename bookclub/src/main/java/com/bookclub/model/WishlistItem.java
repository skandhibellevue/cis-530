package com.bookclub.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public class WishlistItem {
    @NotNull(message = "ISBN is a required field.")
    @NotEmpty(message = "ISBN is a required field.")
    private String isbn;

    @NotNull(message = "Title is a required field.")
    @NotEmpty(message = "Title is a required field.")
    private String title;

    @Id
    private String id;
    private String username;

    // Default constructor
    public WishlistItem() {
    }

    // Parameterized constructor
    public WishlistItem(String isbn, String title, String username) {
        this.isbn = isbn;
        this.title = title;
        this.username = username;
    }

    // Getters and setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getters and setters for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    // Override toString method
    @Override
    public String toString() {
        return "WishlistItem{id=" + id + "isbn=" + isbn + ", title=" + title + "}";
    }
}

