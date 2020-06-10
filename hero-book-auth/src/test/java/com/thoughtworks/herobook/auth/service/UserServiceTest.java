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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivationCodeRepository activationCodeRepository;

    @Mock
    private AmqpTemplate amqpTemplate;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private static PasswordEncoder passwordEncoder;

    @BeforeAll
    @Deprecated
    public static void setUp() {
        String encoderId = "MD5";
        HashMap<String, PasswordEncoder> idToPasswordEncoder = new HashMap<>();
        idToPasswordEncoder.put(encoderId, new MessageDigestPasswordEncoder(encoderId));
        passwordEncoder = new DelegatingPasswordEncoder(encoderId, idToPasswordEncoder);
    }

    @Test
    void should_throw_not_unique_exception_when_user_register_given_email_has_been_registered_and_activated() {
        String email = "123@163.com";
        UserDTO requestDTO = UserDTO.builder().username("Jack").password("123456").email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder().isActivated(true).email(email).build()));

        assertThrows(NotUniqueException.class, () -> userService.userRegistration(requestDTO));
    }

    @Test
    void should_register_successfully_when_user_register_given_email_has_not_been_registered() {
        String email = "123@163.com";
        String username = "Jack";
        String password = "123456";
        UserDTO requestDTO = UserDTO.builder().username(username).password(password).email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        userService.userRegistration(requestDTO);

        verify(userRepository).save(userArgumentCaptor.capture());
        User captorValue = userArgumentCaptor.getValue();
        assertEquals(username, captorValue.getUsername());
        assertTrue(passwordEncoder.matches("123456", captorValue.getPassword()));
        assertEquals(email, captorValue.getEmail());
    }

    @Test
    void should_save_activation_code_when_user_register_successfully() {
        String email = "123@163.com";
        String username = "Jack";
        String password = "123456";
        UserDTO requestDTO = UserDTO.builder().username(username).password(password).email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        userService.userRegistration(requestDTO);

        verify(activationCodeRepository).save(any());
    }

    @Test
    void should_send_message_to_mq_when_user_register_successfully() {
        String email = "123@163.com";
        String username = "Jack";
        String password = "123456";
        UserDTO requestDTO = UserDTO.builder().username(username).password(password).email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        userService.userRegistration(requestDTO);

        verify(amqpTemplate).convertAndSend(anyString(), anyString(), (Object) any());
    }

    @Test
    void should_return_user_when_get_by_email_given_exist_email() {
        var email = "123@163.com";
        var user = User.builder().email(email)
                .username("Jack")
                .id(1L)
                .isActivated(true)
                .password("password")
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        var actualUser = userService.getByEmail(email);

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(user, actualUser);
    }

    @Test
    void should_return_null_when_get_by_email_given_non_exist_email() {
        var email = "123@163.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        var actualUser = userService.getByEmail(email);

        Assertions.assertNull(actualUser);
    }

    @Test
    void should_throw_invalid_email_exception_when_get_by_email_given_invalid_email() {
        var email = "123";

        Assertions.assertThrows(InvalidEmailException.class, () -> userService.getByEmail(email));
    }


    @Test
    void should_throw_null_pointer_exception_when_get_by_email_given_invalid_email() {

        Assertions.assertThrows(NullPointerException.class, () -> userService.getByEmail(null));
    }

    @Test
    void should_throw_code_not_found_exception_when_activate_user_account_given_non_exist_activation_code() {
        String code = "zxdzxcvcxzv";
        when(activationCodeRepository.findByActivationCode(code)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.activateAccount(code));
    }

    @Test
    void should_throw_user_has_been_activated_exception_when_activate_user_account_given_user_has_been_activated() {
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(-2);
        String code = "zxdzxcvcxzv";
        ActivationCode activationCode = ActivationCode.builder()
                .expiredTime(expiredTime)
                .activationCode(code)
                .user(User.builder().isActivated(true).build())
                .build();
        when(activationCodeRepository.findByActivationCode(code)).thenReturn(Optional.of(activationCode));

        assertThrows(UserHasBeenActivatedException.class, () -> userService.activateAccount(code));
    }

    @Test
    void should_throw_code_expired_exception_when_activate_user_account_given_expired_activation_code() {
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(-2);
        String code = "zxdzxcvcxzv";
        ActivationCode activationCode = ActivationCode.builder()
                .expiredTime(expiredTime)
                .activationCode(code)
                .user(User.builder().isActivated(false).build())
                .build();
        when(activationCodeRepository.findByActivationCode(code)).thenReturn(Optional.of(activationCode));

        assertThrows(ExpiredException.class, () -> userService.activateAccount(code));
    }

    @Test
    void should_activate_user_when_activate_user_account_given_valid_activation_code() {
        String code = "zxdzxcvcxzv";
        User user = User.builder().isActivated(false).build();
        ActivationCode activationCode = ActivationCode.builder()
                .expiredTime(LocalDateTime.now().plusDays(1))
                .activationCode(code)
                .user(user)
                .build();
        when(activationCodeRepository.findByActivationCode(code)).thenReturn(Optional.of(activationCode));

        userService.activateAccount(code);

        verify(userRepository).save(userArgumentCaptor.capture());
        User captorValue = userArgumentCaptor.getValue();
        assertTrue(captorValue.getIsActivated());
    }

    @Test
    void should_throw_exception_when_user_registration_given_user_has_not_activated() {
        String email = "123@163.com";
        UserDTO requestDTO = UserDTO.builder().username("Jack").password("123456").email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder().isActivated(false).email(email).build()));

        assertThrows(UserNotActivatedException.class, () -> userService.userRegistration(requestDTO));
    }

    @Test
    void should_throw_exception_when_resend_registration_email_given_user_has_not_been_registered() {
        String email = "123@163.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.resendRegistrationEmail(email));
    }

    @Test
    void should_throw_exception_when_resend_registration_email_given_user_has_activated() {
        String email = "123@163.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder().isActivated(true).email(email).build()));

        assertThrows(UserHasBeenActivatedException.class, () -> userService.resendRegistrationEmail(email));
    }

    @Test
    void should_update_activation_code_when_resend_registration_email() {
        String email = "123@163.com";
        User user = User.builder().isActivated(false).email(email).build();
        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .build();
        user.setActivationCode(activationCode);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        userService.resendRegistrationEmail(email);

        verify(activationCodeRepository).save(any(ActivationCode.class));
    }
}
