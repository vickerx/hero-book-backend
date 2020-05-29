package com.thoughtworks.herobook.gateway.clients;

import com.thoughtworks.herobook.gateway.clients.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "authservice")
public interface UserApiClient {
    UserDto getUserByUsername(String username);
}
