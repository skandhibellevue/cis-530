package com.bookclub.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookOfTheMonthTest {

    @Test
    public void testToString() {
        BookOfTheMonth bookOfTheMonth = new BookOfTheMonth(1, "9780141185064");
        bookOfTheMonth.setId("123");

        String expected = "BookOfTheMonth{id='123', month=1, isbn='9780141185064'}";
        assertEquals(expected, bookOfTheMonth.toString());
    }
}
