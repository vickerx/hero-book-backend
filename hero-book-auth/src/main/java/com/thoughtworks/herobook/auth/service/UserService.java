package com.thoughtworks.herobook.auth.service;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.exception.EmailNotUniqueException;
import com.thoughtworks.herobook.auth.exception.InvalidEmailException;
import com.thoughtworks.herobook.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void userRegistration(UserDTO userDTO) throws EmailNotUniqueException {
        userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
            throw new EmailNotUniqueException("邮箱已被注册");
        });
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()))
                .email(userDTO.getEmail()).build();
        userRepository.save(user);
    }

    public User getByEmail(String email) {
        checkEmail(email);
        return userRepository.findByEmail(email).orElse(null);
    }

    private void checkEmail(String email) {
        if (!email.matches("[\\w.]+@[\\w.]+")) {
            throw new InvalidEmailException();
        }
    }
}
