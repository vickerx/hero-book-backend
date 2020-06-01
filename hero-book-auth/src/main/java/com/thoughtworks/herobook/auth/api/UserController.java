package com.thoughtworks.herobook.auth.api;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/registration")
    public ResponseEntity<Void> userRegistration(UserDTO userDTO) {
        userService.userRegistration(userDTO);
        return ResponseEntity.ok(null);
    }

}
