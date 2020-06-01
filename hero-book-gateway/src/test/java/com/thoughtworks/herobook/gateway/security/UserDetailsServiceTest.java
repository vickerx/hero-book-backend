package com.thoughtworks.herobook.gateway.security;

import com.thoughtworks.herobook.gateway.clients.UserApiClient;
import com.thoughtworks.herobook.gateway.clients.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceTest {

    @Test
    void should_get_user_details_when_load_user_by_username() {
        var username = "username";
        var userApiClient = Mockito.mock(UserApiClient.class);
        var user = UserDto.builder().email(username)
                .password("{bcrypt}$2a$10$VN7b9yjH3/LMyL1PHW5.1Ot0sq7MHzS.4Tqirulq1/EuGrnyLRwW.").build();

        Mockito.when(userApiClient.getUserByEmail(username)).thenReturn(user);

        var userDetailsService = new UserDetailsServiceImpl(userApiClient);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Mockito.verify(userApiClient).getUserByEmail(username);
        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), userDetails.getPassword());

    }

    @Test
    void should_throw_username_not_found_exception_when_load_user_by_username_given_non_user() {
        //given 1
        var username = "";
        var userApiClient = Mockito.mock(UserApiClient.class);
        var userDetailsService = new UserDetailsServiceImpl(userApiClient);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));

        //given 2
        var username2 = "username";
        Mockito.when(userApiClient.getUserByEmail(username)).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username2));

    }

}
