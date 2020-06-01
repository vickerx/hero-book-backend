package com.thoughtworks.herobook.auth.service;

import com.thoughtworks.herobook.auth.dto.UserDTO;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.exception.EmailNotUniqueException;
import com.thoughtworks.herobook.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.DigestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

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
}