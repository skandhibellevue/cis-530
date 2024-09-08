package com.bookclub.service.impl;

import com.bookclub.model.Book;
import com.bookclub.service.dao.BookDao;

import java.util.ArrayList;
import java.util.List;

public class MemBookDao implements BookDao {

    // Private fields
    private List<Book> books;
    // Constructor to initialize the list with five books
    public MemBookDao() {
        books = new ArrayList<>();

        // Add five Book objects to the list
        books.add(new Book(
                "978-3-16-148410-0",
                "The Great Gatsby",
                "A novel about the American dream and the roaring 1920s.",
                180,
                List.of("F. Scott Fitzgerald")
        ));

        books.add(new Book(
                "978-0-452-28423-4",
                "To Kill a Mockingbird",
                "A story of racial injustice in the deep South.",
                281,
                List.of("Harper Lee")
        ));

        books.add(new Book(
                "978-0-7432-7356-5",
                "The Catcher in the Rye",
                "A tale of teenage angst and rebellion.",
                214,
                List.of("J.D. Salinger")
        ));

        books.add(new Book(
                "978-0-14-118506-4",
                "1984",
                "A dystopian novel about a totalitarian regime.",
                328,
                List.of("George Orwell")
        ));

        books.add(new Book(
                "978-0-618-00221-3",
                "The Hobbit",
                "A fantasy adventure story set in Middle-earth.",
                310,
                List.of("J.R.R. Tolkien")
        ));
    }

    @Override
    public List<Book> list() {
        return this.books;
    }

    @Override
    public Book find(String key) {
        for (Book book : this.books) {
            if (book.getIsbn().equals(key)) {
                return book;
            }
        }

        return new Book();
    }
}
