package com.thoughtworks.herobook.gateway.security;

import com.google.common.collect.Lists;
import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.gateway.clients.UserApiClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("email cannot be empty.");
        }
        UserResponse userResponse = userApiClient.getByEmail(username);
        if (userResponse == null) {
            throw new UsernameNotFoundException("email not found.");
        }
        return User.withUsername(username).password(userResponse.getPassword())
                .disabled(!userResponse.getIsActivated())
                .authorities(Lists.newArrayList()).build();
    }
}
