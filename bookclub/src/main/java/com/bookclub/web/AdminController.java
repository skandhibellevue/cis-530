package com.bookclub.web;

import com.bookclub.model.BookOfTheMonth;
import com.bookclub.service.dao.BookOfTheMonthDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/monthly-books")
@CrossOrigin(origins = "*")
public class AdminController {

    private BookOfTheMonthDao bookOfTheMonthDao;

    @Autowired
    public void setBookOfTheMonthDao(BookOfTheMonthDao bookOfTheMonthDao) {
        this.bookOfTheMonthDao = bookOfTheMonthDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showBookOfTheMonth(Model model) {
        List<BookOfTheMonth> books = bookOfTheMonthDao.list("999");
        model.addAttribute("books", books);
        return "monthly-books/list";
    }

    @GetMapping("/api")
    @ResponseBody
    public List<BookOfTheMonth> getMonthlyBooks() {
        return bookOfTheMonthDao.list("999");
    }

    public Map<Integer, String> getMonths() {
        Map<Integer, String> months = new HashMap<>();
        months.put(1, "January");
        months.put(2, "February");
        months.put(3, "March");
        months.put(4, "April");
        months.put(5, "May");
        months.put(6, "June");
        months.put(7, "July");
        months.put(8, "August");
        months.put(9, "September");
        months.put(10, "October");
        months.put(11, "November");
        months.put(12, "December");
        return months;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/new")
    public String bookOfTheMonthForm(Model model) {
        model.addAttribute("months", getMonths());
        model.addAttribute("bookOfTheMonth", new BookOfTheMonth());
        return "monthly-books/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addBookOfTheMonth(@Valid BookOfTheMonth bookOfTheMonth, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("months", getMonths());
            return "monthly-books/new";
        }
        bookOfTheMonthDao.add(bookOfTheMonth);
        return "redirect:/monthly-books";
    }

    @PostMapping("/remove/{id}")
    public String removeBookOfTheMonth(@PathVariable String id) {
        bookOfTheMonthDao.remove(id);
        return "redirect:/monthly-books";
    }
}
