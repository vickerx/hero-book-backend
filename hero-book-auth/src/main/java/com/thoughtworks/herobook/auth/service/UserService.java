package com.thoughtworks.herobook.auth.service;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.entity.ActivationCode;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.exception.ExpiredException;
import com.thoughtworks.herobook.auth.exception.InvalidEmailException;
import com.thoughtworks.herobook.auth.exception.NotUniqueException;
import com.thoughtworks.herobook.auth.exception.UserHasBeenActivatedException;
import com.thoughtworks.herobook.auth.exception.UserNotActivatedException;
import com.thoughtworks.herobook.auth.repository.ActivationCodeRepository;
import com.thoughtworks.herobook.auth.repository.UserRepository;
import com.thoughtworks.herobook.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    public static final String USER_ACTIVATION_URL_PREFIX = "http://localhost:8080/#/signup-active/";
    public static final String EMAIL_EXCHANGE_NAME = "email";
    public static final String USER_REGISTRATION_ROUTING_KEY = "user.registration";
    public static final int ACTIVATION_CODE_EXPIRED_DAYS = 1;

    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final AmqpTemplate amqpTemplate;

    @Transactional
    public void userRegistration(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
            if (!user.getIsActivated()) {
                throw new UserNotActivatedException("The email has not been activated");
            }
            throw new NotUniqueException("The email has been registered");
        });
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(generateEncodedPassword(userDTO.getPassword()))
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

    @SuppressWarnings("deprecation")
    private String generateEncodedPassword(String password) {
        String encoderId = "MD5";
        HashMap<String, PasswordEncoder> idToPasswordEncoder = new HashMap<>();
        idToPasswordEncoder.put(encoderId, new MessageDigestPasswordEncoder(encoderId));
        return new DelegatingPasswordEncoder(encoderId, idToPasswordEncoder).encode(password);
    }

    private void sendRegistrationEmail(UserDTO userDTO, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userDTO.getUsername());
        map.put("emailAddress", userDTO.getEmail());
        map.put("activationLink", USER_ACTIVATION_URL_PREFIX + code);
        amqpTemplate.convertAndSend(EMAIL_EXCHANGE_NAME, USER_REGISTRATION_ROUTING_KEY, map);
    }

    public User getByEmail(String email) {
        checkEmail(email);
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> getListByEmails(List<String> emails) {
        emails.forEach(this::checkEmail);
        return userRepository.findAllByEmailIn(emails);
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
                .orElseThrow(() -> new ResourceNotFoundException("The activation code is not found"));
        validate(activationCode);

        User user = activationCode.getUser();
        user.setIsActivated(true);
        userRepository.save(user);
    }

    private void validate(ActivationCode activationCode) {
        if (activationCode.getUser().getIsActivated()) {
            throw new UserHasBeenActivatedException("The account has been activated, please login");
        }
        if (activationCode.getExpiredTime().isBefore(LocalDateTime.now())) {
            throw new ExpiredException("The activation code is expired");
        }
    }

    @Transactional
    public void resendRegistrationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("The account has not been registered"));
        if (user.getIsActivated()) {
            throw new UserHasBeenActivatedException("The account has been activated, please login");
        }

        String code = UUID.randomUUID().toString().replaceAll("-", "");
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(ACTIVATION_CODE_EXPIRED_DAYS);
        ActivationCode activationCode = user.getActivationCode();
        activationCode.setActivationCode(code);
        activationCode.setExpiredTime(expiredTime);
        activationCodeRepository.save(activationCode);

        UserDTO userDTO = UserDTO.builder().email(email).username(user.getUsername()).build();
        sendRegistrationEmail(userDTO, code);
    }
}
