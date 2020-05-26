package com.thoughtworks.herobook.email.api;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmailControllerTest extends BaseControllerTest {

    @Test
    void should_send_email_successfully_when_user_register_given_username_email_address_and_activation_link() throws Exception {
        mockMvc.perform(post("/user/register")
                .param("username", "Jack")
                .param("emailAddress", "773411997@qq.com")
                .param("activationLink", "http://www.baidu.com"))
                .andExpect(status().isOk());
        verify(emailUtils, only()).sendEmail(any());
    }
}
