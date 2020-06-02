package com.thoughtworks.herobook.auth.service;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.exception.EmailNotUniqueException;
import com.thoughtworks.herobook.auth.repository.ActivationCodeRepository;
import com.thoughtworks.herobook.auth.exception.InvalidEmailException;
import com.thoughtworks.herobook.auth.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.util.DigestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void should_throw_exception_when_user_register_given_email_has_been_registered() {
        String email = "123@163.com";
        UserDTO requestDTO = UserDTO.builder().username("Jack").password("123456").email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder().email(email).build()));

        assertThrows(EmailNotUniqueException.class, () -> userService.userRegistration(requestDTO));
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
        assertEquals(DigestUtils.md5DigestAsHex(password.getBytes()), captorValue.getPassword());
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
}
