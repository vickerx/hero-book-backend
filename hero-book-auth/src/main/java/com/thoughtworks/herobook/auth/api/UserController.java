package com.thoughtworks.herobook.auth.api;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.auth.dto.response.UserInfoResponse;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Void> userRegistration(@Valid UserDTO userDTO) {
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

    @GetMapping("/get-list-by-emails")
    public List<UserResponse> getUserByEmails(@RequestParam("emails") List<String> emails) {
        List<User> users = userService.getListByEmails(emails);
        return users.stream().map(this::of).collect(Collectors.toList());
    }

    @GetMapping("/resend-registration-email")
    public ResponseEntity<Void> resendRegistrationEmail(@RequestParam("email") String email) {
        userService.resendRegistrationEmail(email);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/info")
    public UserInfoResponse getUserInfo(@RequestHeader("email") String email) {
        User user = userService.getByEmail(email);
        return UserInfoResponse.of(user);
    }

    public UserResponse of(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .isActivated(user.getIsActivated())
                .username(user.getUsername())
                .build();
    }
}
