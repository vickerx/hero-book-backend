package com.thoughtworks.herobook.gateway.zuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.netflix.zuul.context.RequestContext;
import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.gateway.AbstractWireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class GatewayFilterIntegrationTest extends AbstractWireMockTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @AfterEach
    public void setUpAndTearDown() {
        //不清理会干扰其他测试
        RequestContext.testSetCurrentContext(null);
    }

    @Test
    public void should_pass_gateway_filter_given_user_is_authenticated() throws Exception {
        var user = UserResponse.builder().email("123@163.com").id(1L).isActivated(true).password("{MD5}{tyl4WmSKtePdnoq1m5eWo+E2NBeW0srEsmAbNo53y4g=}62b9ef3227a44f7a1a9297b65c0d33d4").build();
        stubFor(get(urlPathEqualTo("/user/get-by-email"))
                .willReturn(WireMock.aResponse().withBody(objectMapper.writeValueAsString(user)).withHeader("content-type", "application/json")));

        var setCookie = restTemplate.postForEntity("/login?email={email}&password={password}", null, Map.class, user.getEmail(), "123456").getHeaders().get("Set-Cookie");
        var headers = new HttpHeaders();
        headers.addAll("Cookie", setCookie);

        var requestContext = Mockito.spy(RequestContext.getCurrentContext());
        RequestContext.testSetCurrentContext(requestContext);

        restTemplate.exchange("/testservice/authentication", HttpMethod.POST, new HttpEntity<>(headers), Void.class);

        Mockito.verify(requestContext).addZuulRequestHeader("email", user.getEmail());

    }

}
