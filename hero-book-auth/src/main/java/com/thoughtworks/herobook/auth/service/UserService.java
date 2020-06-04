package com.thoughtworks.herobook.auth.service;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.entity.ActivationCode;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.exception.ExpiredException;
import com.thoughtworks.herobook.auth.exception.UserHasBeenActivatedException;
import com.thoughtworks.herobook.auth.exception.NotFoundException;
import com.thoughtworks.herobook.auth.exception.NotUniqueException;
import com.thoughtworks.herobook.auth.repository.ActivationCodeRepository;
import com.thoughtworks.herobook.auth.exception.InvalidEmailException;
import com.thoughtworks.herobook.auth.repository.UserRepository;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.HttpStatus;
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
    public static final String USER_ACTIVE_URL_PREFIX = "http://localhost:8080/#/signup-active/";
    public static final String EMAIL_EXCHANGE_NAME = "email";
    public static final String USER_REGISTRATION_ROUTING_KEY = "user.registration";
    public static final int ACTIVATION_CODE_EXPIRED_DAYS = 1;

    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final AmqpTemplate amqpTemplate;

    @Transactional
    public void userRegistration(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
            throw new NotUniqueException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_UNIQUE_ERROR, "The email has been registered");
        });
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()))
                .email(userDTO.getEmail())
                .isActivated(false)
                .build();
        userRepository.save(user);

        String code = UUID.randomUUID().toString().replaceAll("-", "");
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(ACTIVATION_CODE_EXPIRED_DAYS);
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
                .findByActivationCode(code)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND, "The activation code is not found"));
        validate(activationCode);

        User user = activationCode.getUser();
        user.setIsActivated(true);
        userRepository.save(user);
    }

    private void validate(ActivationCode activationCode) {
        if (activationCode.getUser().getIsActivated()) {
            throw new UserHasBeenActivatedException(HttpStatus.BAD_REQUEST, ErrorCode.ACCOUNT_HAS_BEEN_ACTIVATED, "The account has been activated, please login");
        }
        if (activationCode.getExpiredTime().isBefore(LocalDateTime.now())) {
            throw new ExpiredException(HttpStatus.BAD_REQUEST, ErrorCode.CODE_HAS_EXPIRED, "The activation code is expired");
        }
    }
}
