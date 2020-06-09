package com.thoughtworks.herobook.auth.api;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.thoughtworks.herobook.auth.entity.ActivationCode;
import com.thoughtworks.herobook.auth.entity.User;
import com.thoughtworks.herobook.auth.repository.ActivationCodeRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        MockitoTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
public class UserControllerTest extends BaseControllerTest {
    @Autowired
    private ActivationCodeRepository activationCodeRepository;

    @Test
    void should_register_user_successfully() throws Exception {
        mockMvc.perform(post("/user/registration")
                .param("username", "Jack")
                .param("password", "12345678")
                .param("email", "123@163.com"))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_400_status_code_when_user_registration_given_invalid_username() throws Exception {
        mockMvc.perform(post("/user/registration")
                .param("username", "")
                .param("password", "12345678")
                .param("email", "123@163.com"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_status_code_when_user_registration_given_invalid_password() throws Exception {
        mockMvc.perform(post("/user/registration")
                .param("username", "")
                .param("password", "123456")
                .param("email", "123@163.com"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_status_code_when_user_registration_given_invalid_email_address() throws Exception {
        mockMvc.perform(post("/user/registration")
                .param("username", "")
                .param("password", "123456")
                .param("email", "123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_active_user_account_after_user_registration_given_valid_activation_code() throws Exception {
        User user = User.builder()
                .username("Jack")
                .email("123@163.com")
                .password("123456")
                .isActivated(false)
                .build();
        String code = "xzcvcvxczv";
        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .activationCode(code)
                .expiredTime(LocalDateTime.now().plusDays(1))
                .build();
        activationCodeRepository.save(activationCode);

        mockMvc.perform(get("/user/activate")
                .param("code", code))
                .andExpect(status().isOk());
    }

    @Test
    @DatabaseSetup("/dbunit/UserControllerTest/should_return_user_when_get_by_email_setup.xml")
    @DatabaseTearDown("/dbunit/UserControllerTest/user_clear_all.xml")
    void should_return_user_when_get_by_email() throws Exception {
        var email = "123@163.com";
        mockMvc.perform(get("/user/get-by-email").param("email", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("nick")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("aaaaaaaa")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActivated", Matchers.is(true)));
    }

    @Test
    void should_return_null_when_get_by_email() throws Exception {
        var email = "123@163.com";
        mockMvc.perform(get("/user/get-by-email").param("email", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.emptyOrNullString()));
    }
}
