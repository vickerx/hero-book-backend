package com.thoughtworks.herobook.auth.service;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.entity.ActivationCode;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.exception.EmailNotUniqueException;
import com.thoughtworks.herobook.auth.repository.ActivationCodeRepository;
import com.thoughtworks.herobook.auth.exception.InvalidEmailException;
import com.thoughtworks.herobook.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;

    @Transactional
    public void userRegistration(UserDTO userDTO) throws EmailNotUniqueException {
        userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
            throw new EmailNotUniqueException("邮箱已被注册");
        });
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()))
                .email(userDTO.getEmail()).build();
        userRepository.save(user);

        String code = UUID.randomUUID().toString().replaceAll("-","");
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(1L);
        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .expiredTime(expiredTime)
                .activationCode(code).build();
        activationCodeRepository.save(activationCode);
    }

    public User getByEmail(String email) {
        checkEmail(email);
        return userRepository.findByEmail(email).orElse(null);
    }

    private void checkEmail(String email) {
        if (email == null) {
            throw new NullPointerException();
        }

        if (!email.matches("[\\w.]+@[\\w.]+")) {
            throw new InvalidEmailException();
        }
    }
}
