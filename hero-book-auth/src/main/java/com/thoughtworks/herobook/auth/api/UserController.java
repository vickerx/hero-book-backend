package com.thoughtworks.herobook.auth.api;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Void> userRegistration(UserDTO userDTO) {
        userService.userRegistration(userDTO);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/activate")
    public ResponseEntity<Void> activateAccount(@RequestParam("code") String code) {
        userService.activateAccount(code);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/get-by-email")
    public UserResponse getByEmail(@RequestParam("email") String email) {
        return of(userService.getByEmail(email));
    }

    public UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .isActivated(user.getIsActivated())
                .username(user.getUsername())
                .build();
    }
}
