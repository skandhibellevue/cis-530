package com.bookclub.web;

import com.bookclub.model.Book;
import com.bookclub.model.BookOfTheMonth;
import com.bookclub.service.dao.BookDao;
import com.bookclub.service.dao.BookOfTheMonthDao;
import com.bookclub.service.impl.RestBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    private BookOfTheMonthDao bookOfTheMonthDao;
    private BookDao bookDao;

    @Autowired
    public void setBookOfTheMonthDao(BookOfTheMonthDao bookOfTheMonthDao) {
        this.bookOfTheMonthDao = bookOfTheMonthDao;
    }

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        int currentMonth = LocalDate.now().getMonthValue();

        // Get the BookOfTheMonth for the current month
        List<BookOfTheMonth> booksOfTheMonth = bookOfTheMonthDao.list(String.valueOf(currentMonth));

        // Build ISBN string
        StringBuilder isbnBuilder = new StringBuilder();
        for (BookOfTheMonth botm : booksOfTheMonth) {
            if (!isbnBuilder.isEmpty()) {
                isbnBuilder.append(",");
            }
            isbnBuilder.append(botm.getIsbn());
        }
        String isbnString = isbnBuilder.toString();

        // Pass the isbnString to bookDao.list()
        List<Book> books = bookDao.list(isbnString);

        model.addAttribute("books", books);
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

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getMonthlyBook(@PathVariable("id") String id, Model model) {
        String isbn = id;
        System.out.println(id);

        RestBookDao bookDao = new RestBookDao();
        Book book = bookDao.find(isbn);
        System.out.println(book.toString());

        model.addAttribute("book", book);
        return "monthly-books/view";
    }
}