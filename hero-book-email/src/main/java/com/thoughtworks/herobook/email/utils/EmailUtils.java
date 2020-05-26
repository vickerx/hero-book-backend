package com.thoughtworks.herobook.email.utils;

import com.thoughtworks.herobook.email.vo.EmailVO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EmailUtils {
    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public void sendEmail(EmailVO emailVO) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(mailProperties.getUsername());
        messageHelper.setSubject(emailVO.getSubject());
        messageHelper.setTo(emailVO.getTargetEmailAddress());

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(emailVO.getTemplateName());
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailVO.getModel());
        messageHelper.setText(content, true);

        javaMailSender.send(mimeMessage);
    }
}
