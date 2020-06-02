package com.thoughtworks.herobook.email.mq;

import com.thoughtworks.herobook.email.enums.EmailType;
import com.thoughtworks.herobook.email.utils.EmailUtils;
import com.thoughtworks.herobook.email.vo.EmailVO;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "user.registration.queue"),
        exchange = @Exchange(name = "email"),
        key = "user.registration"))
@RequiredArgsConstructor
public class UserRegistrationListener {
    private final EmailUtils emailUtils;

    @RabbitHandler
    public void receive(Map<String, String> map) throws MessagingException, IOException, TemplateException {
        EmailVO emailVO = EmailVO.builder()
                .templateName(EmailType.USER_REGISTRATION.getTemplateName())
                .subject(EmailType.USER_REGISTRATION.getSubject())
                .targetEmailAddress(map.get("emailAddress")).model(map).build();

        emailUtils.sendEmail(emailVO);
    }
}
