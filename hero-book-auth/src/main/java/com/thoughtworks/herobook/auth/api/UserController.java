package com.thoughtworks.herobook.auth.api;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/user/registration")
    public ResponseEntity<Void> userRegistration(UserDTO userDTO) {
        return ResponseEntity.ok(null);
    }

}
