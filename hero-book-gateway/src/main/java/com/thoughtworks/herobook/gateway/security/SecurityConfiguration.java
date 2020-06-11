package com.thoughtworks.herobook.gateway.security;

import com.thoughtworks.herobook.gateway.clients.UserApiClient;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserApiClient userApiClient;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(Constants.NOT_AUTHENTICATED_URLS).anonymous()
                .antMatchers(Constants.AUTHENTICATED_URLS).authenticated()
                .antMatchers(HttpMethod.POST).authenticated().and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) ->
                        setResponse(response, HttpStatus.SC_UNAUTHORIZED, 3, "未授权，请登录"))
                .and().formLogin()
                .usernameParameter("email")
                .successHandler(((request, response, authentication) ->
                        setResponse(response, HttpStatus.SC_OK, 0, "success")))
                .failureHandler(((request, response, exception) -> {
                    if (exception instanceof DisabledException) {
                        setResponse(response, HttpStatus.SC_OK, 2, "账号未激活，请登录邮箱激活");
                    } else {
                        setResponse(response, HttpStatus.SC_OK, 1, "邮箱、密码错误或不存在");
                    }
                })).and().logout()
                .logoutSuccessHandler(((request, response, authentication) ->
                        setResponse(response, HttpStatus.SC_OK, 0, "success")));
    }

    private void setResponse(HttpServletResponse response, int httpStatus, int code, String msg) throws IOException {
        response.setContentType("application/json;charset=utf8");
        response.getWriter().write("{\"code\":" + code + ",\"msg\":\"" + msg + "\"}");
        response.setStatus(httpStatus);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userApiClient);
    }

    /**
     * @return passwordEncoder
     * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
     */
    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        String encodingId = "MD5";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put(encodingId, new MessageDigestPasswordEncoder("MD5"));
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

}
