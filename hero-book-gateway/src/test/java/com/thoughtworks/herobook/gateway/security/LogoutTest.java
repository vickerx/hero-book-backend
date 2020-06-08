package com.thoughtworks.herobook.gateway.security;

import com.thoughtworks.herobook.gateway.AbstractWireMockTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
public class LogoutTest extends AbstractWireMockTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void should_logout_successfully_when_logout_given_logged_in() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(0)));
    }
}
