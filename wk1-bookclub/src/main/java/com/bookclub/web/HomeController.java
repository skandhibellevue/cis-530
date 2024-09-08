package com.bookclub.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome() {
        return "index";
    }

    @GetMapping("/about")
    public String showAboutUs(Model model)
    {
        return "about.html";
    }

    @GetMapping("/contact")
    public String showContactUs(Model model)
    {
        return "contact.html";
    }
}