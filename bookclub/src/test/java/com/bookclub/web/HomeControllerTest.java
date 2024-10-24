package com.bookclub.web;

import com.bookclub.service.dao.BookDao;
import com.bookclub.service.dao.BookOfTheMonthDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private BookOfTheMonthDao bookOfTheMonthDao;

    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // Mock a user role if that's enough to access the home route
    public void testShowHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())  // Expecting HTTP 200 OK
                .andExpect(view().name("index"));  // The expected view name
    }
}
