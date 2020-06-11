package com.thoughtworks.herobook.auth.api;

import com.thoughtworks.herobook.auth.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/user")
public interface UserApi {
    @GetMapping("/get-by-email")
    UserResponse getByEmail(@RequestParam("email") String email);

    @GetMapping("/get-list-by-emails")
    List<UserResponse> getUserByEmails(@RequestParam("emails") List<String> emails);
}
