package com.bookclub.service.impl;

import com.bookclub.model.WishlistItem;
import com.bookclub.service.dao.WishlistDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemWishlistDao implements WishlistDao {

    private final List<WishlistItem> wishlist;

    // Constructor to populate wishlist
    public MemWishlistDao() {
        wishlist = new ArrayList<>();
        wishlist.add(new WishlistItem("1234567890", "The Great Gatsby"));
        wishlist.add(new WishlistItem("0987654321", "To Kill a Mockingbird"));
    }

    @Override
    public List<WishlistItem> list() {
        return wishlist;
    }

    @Override
    public WishlistItem find(String isbn) {
        Optional<WishlistItem> item = wishlist.stream()
                .filter(w -> w.getIsbn().equals(isbn))
                .findFirst();
        return item.orElse(null);
    }
}

