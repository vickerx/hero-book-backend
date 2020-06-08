package com.thoughtworks.herobook.gateway.zuul;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class GatewayFilterUnitTest {

    @Test
    public void should_add_username_to_zuul_header_given_user_is_authenticated() throws ZuulException {
        var userDetails = User.withUsername("123@163.com").password("123456").authorities(List.of()).build();
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        var requestContext = Mockito.mock(RequestContext.class);
        RequestContext.testSetCurrentContext(requestContext);
        SecurityContextHolder.setContext(Mockito.mock(SecurityContext.class));
        Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

        var gateway = new GatewayFilter();
        gateway.run();

        Mockito.verify(requestContext).addZuulRequestHeader("email", "123@163.com");
    }

    @Test
    public void should_skip_username_to_zuul_header_given_user_is_not_authenticated() throws ZuulException {
        var requestContext = Mockito.mock(RequestContext.class);
        RequestContext.testSetCurrentContext(requestContext);
        SecurityContextHolder.setContext(Mockito.mock(SecurityContext.class));
        Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(null);

        var gateway = new GatewayFilter();
        gateway.run();

        Mockito.verify(requestContext, Mockito.never()).addZuulRequestHeader(Mockito.anyString(), Mockito.anyString());
    }
}
