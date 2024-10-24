package com.bookclub.service.impl;

import com.bookclub.model.BookOfTheMonth;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig
public class MongoBookOfTheMonthDaoTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MongoBookOfTheMonthDao dao; // Automatically injects the mock mongoTemplate

    @Test
    public void testAddBookOfTheMonth() {
        BookOfTheMonth bookOfTheMonth = new BookOfTheMonth(1, "9780141185064");

        dao.add(bookOfTheMonth);

        // Verifying that the mongoTemplate's save method was called with the correct parameters
        verify(mongoTemplate).save(any(BookOfTheMonth.class), any(String.class));
    }
}


