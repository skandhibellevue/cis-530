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

    private static final String COLLECTION_NAME = "wishlist";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<WishlistItem> list(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));  // Filter by username
        return mongoTemplate.find(query, WishlistItem.class, COLLECTION_NAME);
    }

    @Override
    public WishlistItem find(String key) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(key));  // Find by ID
        return mongoTemplate.findOne(query, WishlistItem.class, COLLECTION_NAME);
    }

    @Override
    public void add(WishlistItem entity) {
        mongoTemplate.save(entity, COLLECTION_NAME);
    }

    @Override
    public void update(WishlistItem entity) {
        WishlistItem wishlistItem = mongoTemplate.findById(entity.getId(), WishlistItem.class, COLLECTION_NAME);

        if (wishlistItem != null) {
            wishlistItem.setIsbn(entity.getIsbn());
            wishlistItem.setTitle(entity.getTitle());
            wishlistItem.setUsername(entity.getUsername());
            mongoTemplate.save(wishlistItem, COLLECTION_NAME);
        }
    }

    @Override
    public boolean remove(String key) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(key));
        mongoTemplate.remove(query, WishlistItem.class, COLLECTION_NAME);
        return true;
    }
}
