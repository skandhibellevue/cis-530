package com.bookclub.service.impl;

import com.bookclub.model.Book;
import com.bookclub.service.dao.BookDao;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestBookDao implements BookDao {

    @Override
    public List<Book> list(String isbnString) {
        List<Book> books = new ArrayList<>();

        try {
            // Call the getBooksDoc() method to fetch the book data
            Object jsonBooks = getBooksDoc(isbnString);

            // Use JsonPath to extract the relevant data for each book and build a list of books
            List<String> titles = JsonPath.read(jsonBooks, "$..title");
            List<String> isbns = JsonPath.read(jsonBooks, "$..bib_key");
            List<String> descriptions = JsonPath.read(jsonBooks, "$..details.subtitle");
            List<String> infoUrls = JsonPath.read(jsonBooks, "$..info_url");
            List<Integer> numOfPages = JsonPath.read(jsonBooks, "$..number_of_pages");

            for (int index = 0; index < titles.size(); index++) {
                Book book = new Book();
                book.setTitle(titles.get(index));
                book.setIsbn(index < isbns.size() ? isbns.get(index) : "N/A");
                book.setDescription(index < descriptions.size() ? descriptions.get(index) : "N/A");
                book.setInfoUrl(index < infoUrls.size() ? infoUrls.get(index) : "N/A");
                book.setNumOfPages(index < numOfPages.size() ? numOfPages.get(index) : 0);

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Book find(String isbn) {
        // Call the getBooksDoc() method to fetch data for a single book
        Object jsonBook = getBooksDoc(isbn);

        // Extract the book information using JsonPath
        List<String> isbns = JsonPath.read(jsonBook, "$..bib_key");
        List<String> titles = JsonPath.read(jsonBook, "$..title");
        List<String> descriptions = JsonPath.read(jsonBook, "$..details.subtitle");
        List<String> infoUrls = JsonPath.read(jsonBook, "$..info_url");
        List<Integer> numOfPages = JsonPath.read(jsonBook, "$..number_of_pages");

        // Inline conditional checks to safely extract values or assign default values
        String bookIsbn = isbns.size() > 0 ? isbns.get(0) : "N/A";
        String bookTitle = titles.size() > 0 ? titles.get(0) : "N/A";
        String bookDescription = descriptions.size() > 0 ? descriptions.get(0) : "N/A";
        String bookInfoUrl = infoUrls.size() > 0 ? infoUrls.get(0) : "N/A";
        int bookNumOfPages = numOfPages.size() > 0 ? numOfPages.get(0) : 0;

        // Create and return the Book object
        return new Book(bookIsbn, bookTitle, bookDescription, bookInfoUrl, bookNumOfPages);
    }

    // Method to fetch book data from OpenLibrary API by ISBN using RestTemplate
    public Object getBooksDoc(String isbnString) {
        // Create local variable for the OpenLibrary API URL
        String openLibraryUrl = "https://openlibrary.org/api/books";

        // Instantiate RestTemplate
        RestTemplate rest = new RestTemplate();

        // Instantiate HttpHeaders and set the Accept header
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        // Build the URI with the required query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(openLibraryUrl)
                .queryParam("bibkeys", "ISBN:" + isbnString)
                .queryParam("format", "json")
                .queryParam("jscmd", "details");

        // Create a new HttpEntity with headers
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Create a new HttpEntity for the response and make the API call
        HttpEntity<String> response = rest.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        // Bind the response body to a String variable and parse the JSON
        String jsonBooklist = response.getBody();
        return Configuration.defaultConfiguration().jsonProvider().parse(jsonBooklist);
    }
}
