package com.bookclub.web;

import com.bookclub.model.WishlistItem;
import com.bookclub.service.dao.WishlistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/wishlist", produces = "application/json")
@CrossOrigin(origins = "*")
public class WishlistRestController {

    private WishlistDao wishlistDao;

    // Setter method with @Autowired for dependency injection
    @Autowired
    public void setWishlistDao(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    // GET method to return the list of wishlist items
    @GetMapping
    public List<WishlistItem> showWishlist(Authentication authentication) {
        String username = authentication.getName();
        List<WishlistItem> wishes = wishlistDao.list(username);
        return wishes;  // Pass in the username
    }

    // GET method to find wishlist item by ID
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public WishlistItem findById(@PathVariable String id) {
        return wishlistDao.find(id);
    }
}
