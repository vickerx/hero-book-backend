package com.thoughtworks.herobook.gateway.clients;

import com.thoughtworks.herobook.gateway.clients.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authservice")
@RequestMapping("/user")
public interface UserApiClient {

    @GetMapping("/get-user-by-email")
    UserDto getUserByEmail(@RequestParam("email") String email);
}
