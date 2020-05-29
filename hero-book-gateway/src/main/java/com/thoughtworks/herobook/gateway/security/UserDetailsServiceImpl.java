package com.thoughtworks.herobook.gateway.security;

import com.google.common.collect.Lists;
import com.thoughtworks.herobook.gateway.clients.UserApiClient;
import com.thoughtworks.herobook.gateway.clients.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserApiClient userApiClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userApiClient.getUserByUsername(username);
        return User.withUsername(username).password(userDto.getPassword())
                .authorities(Lists.newArrayList()).build();
    }
}
