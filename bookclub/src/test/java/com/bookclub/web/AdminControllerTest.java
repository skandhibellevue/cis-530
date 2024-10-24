package com.bookclub.web;

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

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookOfTheMonthDao bookOfTheMonthDao;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simulates an admin user
    public void testShowBookOfTheMonth() throws Exception {
        mockMvc.perform(get("/monthly-books"))
                .andExpect(status().isOk()) // Expect HTTP 200 (OK) since user is authenticated
                .andExpect(view().name("monthly-books/list"));
    }
}
