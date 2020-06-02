package com.thoughtworks.herobook.auth.api;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.dto.response.UserResponse;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Void> userRegistration(UserDTO userDTO) {
        userService.userRegistration(userDTO);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/get-by-email")
    public UserResponse getByEmail(@RequestParam("email") String email) {
        return UserResponse.of(userService.getByEmail(email));
    }
}
