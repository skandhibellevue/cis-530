package com.bookclub.web;

import com.bookclub.model.WishlistItem;
import com.bookclub.service.impl.MemWishlistDao;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    // Show wishlist
    @GetMapping
    public String showWishlist(Model model) {
        MemWishlistDao dao = new MemWishlistDao();
        List<WishlistItem> wishlist = dao.list();
        model.addAttribute("wishlist", wishlist);
        return "wishlist/list";
    }

    // Wishlist form
    @GetMapping("/new")
    public String wishlistForm(Model model) {
        model.addAttribute("wishlistItem", new WishlistItem());
        return "wishlist/new";
    }

    // Add wishlist item
    @PostMapping
    public String addWishlistItem(@Valid WishlistItem wishlistItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "wishlist/new";
        }
        return "redirect:/wishlist";
    }
}

