package com.thoughtworks.herobook.email.service;

import com.thoughtworks.herobook.email.enums.EmailType;
import com.thoughtworks.herobook.email.utils.EmailUtils;
import com.thoughtworks.herobook.email.vo.EmailVO;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailUtils emailUtils;

    public void sendRegistrationEmail(String username, String emailAddress, String activationLink) throws MessagingException, IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("activationLink", activationLink);
        EmailVO emailVO = EmailVO.builder()
                .templateName(EmailType.USER_REGISTRATION.getTemplateName())
                .subject(EmailType.USER_REGISTRATION.getSubject())
                .targetEmailAddress(emailAddress).model(model).build();

        emailUtils.sendEmail(emailVO);
    }
}
