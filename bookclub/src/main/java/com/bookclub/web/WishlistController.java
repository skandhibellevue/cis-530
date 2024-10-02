package com.bookclub.web;

import com.bookclub.model.WishlistItem;
import com.bookclub.service.dao.WishlistDao;
import com.bookclub.service.impl.MongoWishlistDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private WishlistDao wishlistDao = new MongoWishlistDao();

    // Show wishlist
    @GetMapping
    public String showWishlist() {
        return "wishlist/list";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public String showWishlistItem(@PathVariable String id, Model model) {
        WishlistItem wishlistItem = wishlistDao.find(id);
        model.addAttribute("wishlistItem", wishlistItem);
        return "wishlist/view";
    }

    // Wishlist form
    @GetMapping("/new")
    public String wishlistForm(Model model) {
        model.addAttribute("wishlistItem", new WishlistItem());
        return "wishlist/new";
    }

    // Add wishlist item
    @PostMapping
    public String addWishlistItem(@Valid WishlistItem wishlistItem, BindingResult bindingResult, Authentication authentication) {
        String username = authentication.getName();
        if (bindingResult.hasErrors()) {
            return "wishlist/new";
        }
        wishlistItem.setUsername(username);
        wishlistDao.add(wishlistItem);

        return "redirect:/wishlist";
    }

    // View a specific wishlist item by its ID
    @GetMapping("/view/{id}")
    public String viewWishlistItem(@PathVariable String id, Model model) {
        WishlistItem wishlistItem = wishlistDao.find(id);
        model.addAttribute("wishlistItem", wishlistItem);
        return "wishlist/view";  // Return the view page
    }

    // Update Wishlist Item
    @RequestMapping(method = RequestMethod.POST, path = "/update")
    public String updateWishlistItem(@Valid WishlistItem wishlistItem, BindingResult bindingResult, Authentication authentication) {
        wishlistItem.setUsername(authentication.getName());

        if (bindingResult.hasErrors()) {
            return "wishlist/view";
        }
        wishlistDao.update(wishlistItem);

        return "redirect:/wishlist";
    }

    // Remove Wishlist Item
    @PostMapping("/remove/{id}")
    public String removeWishlistItem(@PathVariable String id) {
        wishlistDao.remove(id);
        return "redirect:/wishlist";
    }

    @Autowired
    private void setWishlistDao(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }
}

