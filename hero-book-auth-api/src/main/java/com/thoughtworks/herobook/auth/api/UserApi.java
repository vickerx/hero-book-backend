package com.thoughtworks.herobook.auth.api;

import com.thoughtworks.herobook.auth.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
public interface UserApi {
    @GetMapping("/get-by-email")
    UserResponse getByEmail(@RequestParam("email") String email);
}
