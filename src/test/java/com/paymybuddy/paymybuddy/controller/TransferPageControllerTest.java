package com.paymybuddy.paymybuddy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferPageControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    TransferPageController transferPageController;

    @Test
    public void transferPage() throws Exception {

        mockMvc.perform(get("/transferPage")
                        .param("page", "1")
                        .param("size", "3")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.view().name("transferPage"))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    public void addTransaction() {
    }
}
