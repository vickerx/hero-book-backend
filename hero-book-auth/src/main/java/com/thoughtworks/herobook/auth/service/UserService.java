package com.thoughtworks.herobook.auth.service;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.entity.ActivationCode;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.exception.CodeExpiredException;
import com.thoughtworks.herobook.auth.exception.CodeHasBeenActivated;
import com.thoughtworks.herobook.auth.exception.CodeNotFoundException;
import com.thoughtworks.herobook.auth.exception.EmailNotUniqueException;
import com.thoughtworks.herobook.auth.repository.ActivationCodeRepository;
import com.thoughtworks.herobook.auth.exception.InvalidEmailException;
import com.thoughtworks.herobook.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    public static final String USER_ACTIVE_URL_PREFIX = "http://localhost:8083/user/active?code=";
    public static final String EMAIL_EXCHANGE_NAME = "email";
    public static final String USER_REGISTRATION_ROUTING_KEY = "user.registration";

    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final AmqpTemplate amqpTemplate;

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

        String code = UUID.randomUUID().toString().replaceAll("-", "");
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(1L);
        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .expiredTime(expiredTime)
                .activationCode(code).build();
        activationCodeRepository.save(activationCode);

        sendRegistrationEmail(userDTO, code);
    }

    private void sendRegistrationEmail(UserDTO userDTO, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userDTO.getUsername());
        map.put("emailAddress", userDTO.getEmail());
        map.put("activationLink", USER_ACTIVE_URL_PREFIX + code);
        amqpTemplate.convertAndSend(EMAIL_EXCHANGE_NAME, USER_REGISTRATION_ROUTING_KEY, map);
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

    public void activateAccount(String code) {
        ActivationCode activationCode = activationCodeRepository
                .findByActivationCode(code).orElseThrow(CodeNotFoundException::new);
        User user = activationCode.getUser();
        if (user.getIsActivated()) {
            throw new CodeHasBeenActivated("帐户已激活，请登录");
        }
        if (activationCode.getExpiredTime().isBefore(LocalDateTime.now())) {
            throw new CodeExpiredException("激活码已失效");
        }
        user.setIsActivated(true);
        userRepository.save(user);
    }
}
