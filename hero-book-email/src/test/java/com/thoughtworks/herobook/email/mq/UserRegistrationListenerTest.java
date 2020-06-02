package com.thoughtworks.herobook.email.mq;

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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationListenerTest {
    @Mock
    private EmailUtils emailUtils;

    @InjectMocks
    private UserRegistrationListener userRegistrationListener;

    @Captor
    private ArgumentCaptor<EmailVO> emailVOArgumentCaptor;

    @Test
    void should_call_send_email_when_listener_receive_message() throws MessagingException, IOException, TemplateException {
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("username", "Jack");
        mockMap.put("emailAddress", "123@163.com");
        mockMap.put("activationLink", "www.baidu.com");

        userRegistrationListener.receive(mockMap);

        verify(emailUtils).sendEmail(emailVOArgumentCaptor.capture());
        EmailVO captorValue = emailVOArgumentCaptor.getValue();
        assertEquals(EmailType.USER_REGISTRATION.getSubject(), captorValue.getSubject());
        assertEquals(EmailType.USER_REGISTRATION.getTemplateName(), captorValue.getTemplateName());
        assertEquals("123@163.com", captorValue.getTargetEmailAddress());
        assertEquals("Jack", captorValue.getModel().get("username"));
        assertEquals("www.baidu.com",captorValue.getModel().get("activationLink"));
    }
}
