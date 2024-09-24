package com.bookclub.service.impl;

import com.bookclub.model.WishlistItem;
import com.bookclub.service.dao.WishlistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Repository("wishlistDao")
public class MongoWishlistDao implements WishlistDao {

    private static final String COLLECTION_NAME = "bookclub";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void add(WishlistItem entity) {
        mongoTemplate.save(entity, COLLECTION_NAME);
    }

    @Override
    public void update(WishlistItem entity) {
        mongoTemplate.save(entity, COLLECTION_NAME);  // This will replace if entity with same ID exists
    }

    @Override
    public boolean remove(WishlistItem entity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(entity.getId()));
        mongoTemplate.remove(query, WishlistItem.class, COLLECTION_NAME);
        return true;  // Assuming successful removal
    }

    @Override
    public List<WishlistItem> list() {
        return mongoTemplate.findAll(WishlistItem.class, COLLECTION_NAME);
    }

    @Override
    public WishlistItem find(String key) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(key));  // Find by ID or key
        return mongoTemplate.findOne(query, WishlistItem.class, COLLECTION_NAME);
    }
}
