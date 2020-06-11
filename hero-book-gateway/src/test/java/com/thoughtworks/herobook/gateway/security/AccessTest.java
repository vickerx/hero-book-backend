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
public class AccessTest extends AbstractWireMockTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void should_be_not_access_when_access_authenticated_url_without_authenticated() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/authservice/user/info"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(3)));
    }
}
