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

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserApiClient userApiClient;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers(Constants.NOT_AUTHENTICATED_URLS).anonymous()
                .antMatchers(HttpMethod.POST).authenticated().and()
                .formLogin().usernameParameter("email")
                .successHandler(((request, response, authentication) -> {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"code\":0,\"msg\":\"success\"}");
                    response.setStatus(HttpStatus.SC_OK);
                }))
                .failureHandler(((request, response, exception) -> {
                    if (exception instanceof DisabledException) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"code\":2,\"msg\":\"账号未激活，请登录邮箱激活\"}");
                        response.setStatus(HttpStatus.SC_OK);
                    } else {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"code\":1,\"msg\":\"邮箱、密码错误或不存在\"}");
                        response.setStatus(HttpStatus.SC_OK);
                    }
                })).and().logout()
                .logoutSuccessHandler(((request, response, authentication) -> {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"code\":0,\"msg\":\"success\"}");
                    response.setStatus(HttpStatus.SC_OK);
                }));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        super.configure(auth);
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
