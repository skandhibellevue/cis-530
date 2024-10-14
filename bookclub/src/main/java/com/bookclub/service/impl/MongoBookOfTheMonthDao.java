package com.bookclub.service.impl;

import com.bookclub.model.BookOfTheMonth;
import com.bookclub.service.dao.BookOfTheMonthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

@Repository("bookOfTheMonthDao")
public class MongoBookOfTheMonthDao implements BookOfTheMonthDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "bookclub";

    @Override
    public void add(BookOfTheMonth entity) {
        if (entity.getId() == null || entity.getId().isEmpty()) {
            entity.setId(UUID.randomUUID().toString());  // Generate a unique ID
        }
        mongoTemplate.save(entity, COLLECTION_NAME);
    }

    @Override
    public void update(BookOfTheMonth entity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(entity.getId()));
        BookOfTheMonth existing = mongoTemplate.findOne(query, BookOfTheMonth.class, COLLECTION_NAME);
        if (existing != null) {
            existing.setMonth(entity.getMonth());
            existing.setIsbn(entity.getIsbn());
            mongoTemplate.save(existing, COLLECTION_NAME);
        }
    }

    @Override
    public boolean remove(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, BookOfTheMonth.class, COLLECTION_NAME);
        return true;
    }

    @Override
    public List<BookOfTheMonth> list(String key) {
        int month = Integer.parseInt(key);
        if (month == 999) {
            return mongoTemplate.findAll(BookOfTheMonth.class, COLLECTION_NAME);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("month").is(month));

        return mongoTemplate.find(query, BookOfTheMonth.class, COLLECTION_NAME);
    }

    @Override
    public BookOfTheMonth find(String key) {
        return mongoTemplate.findById(key, BookOfTheMonth.class, COLLECTION_NAME);
    }
}

