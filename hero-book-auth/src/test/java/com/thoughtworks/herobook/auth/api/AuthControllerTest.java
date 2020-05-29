package com.thoughtworks.herobook.auth.api;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseControllerTest{

    @Test
    void should_save_user_to_database() throws Exception {
        mockMvc.perform(post("/user/registration")
                .param("username", "Jack")
                .param("password", "123456")
                .param("email", "123@163.com"))
                .andExpect(status().isOk());
    }
}
