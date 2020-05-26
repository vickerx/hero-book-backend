package com.thoughtworks.herobook.email.service;

import com.thoughtworks.herobook.email.enums.EmailType;
import com.thoughtworks.herobook.email.utils.EmailUtils;
import com.thoughtworks.herobook.email.vo.EmailVO;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private EmailUtils emailUtils;

    @InjectMocks
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<EmailVO> emailVOArgumentCaptor;

    @Test
    void should_call_utils_to_send_registration_email_when_user_register() throws MessagingException, IOException, TemplateException {
        String username = "Jack";
        String targetEmailAddress = "123@163.com";
        String activationLink = "http://www.baidu.com";

        emailService.sendRegistrationEmail(username, targetEmailAddress, activationLink);

        verify(emailUtils, only()).sendEmail(emailVOArgumentCaptor.capture());
        EmailVO emailVO = emailVOArgumentCaptor.getValue();
        assertEquals(emailVO.getSubject(), EmailType.USER_REGISTRATION.getSubject());
        assertEquals(emailVO.getTemplateName(), EmailType.USER_REGISTRATION.getTemplateName());
        assertEquals(emailVO.getTargetEmailAddress(), targetEmailAddress);
        assertEquals(emailVO.getModel().get("username"), username);
        assertEquals(emailVO.getModel().get("activationLink"), activationLink);
    }
}
