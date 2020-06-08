package com.thoughtworks.herobook.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.gateway.AbstractWireMockTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

@AutoConfigureMockMvc
public class LoginTest extends AbstractWireMockTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_login_successfully_when_login_given_correct_email_and_password() throws Exception {

        var user = UserResponse.builder().email("123@163.com").id(1L).isActivated(true).password("{MD5}{tyl4WmSKtePdnoq1m5eWo+E2NBeW0srEsmAbNo53y4g=}62b9ef3227a44f7a1a9297b65c0d33d4").build();
        stubFor(get(urlPathEqualTo("/user/get-by-email"))
                .willReturn(WireMock.aResponse().withBody(objectMapper.writeValueAsString(user)).withHeader("content-type", "application/json")));

        mvc.perform(MockMvcRequestBuilders.post("/login").param("email", "123@163.com").param("password", "123456"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(0)));
    }

    @Test
    public void should_login_failure_when_login_given_correct_email_and_error_password() throws Exception {
        var user = UserResponse.builder().email("123@163.com").id(1L).isActivated(true).password("{MD5}{tyl4WmSKtePdnoq1m5eWo+E2NBeW0srEsmAbNo53y4g=}62b9ef3227a44f7a1a9297b65c0d33d4").build();
        stubFor(get(urlPathEqualTo("/user/get-by-email"))
                .willReturn(WireMock.aResponse().withBody(objectMapper.writeValueAsString(user)).withHeader("content-type", "application/json")));

        mvc.perform(MockMvcRequestBuilders.post("/login").param("email", "123@163.com").param("password", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(1)));

    }

}
