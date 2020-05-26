package com.thoughtworks.herobook.email.api;

import com.thoughtworks.herobook.email.service.EmailService;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/user/register")
    public ResponseEntity<Void> userRegistration(@RequestParam("username") String username,
                                                 @RequestParam("emailAddress") String emailAddress,
                                                 @RequestParam("activationLink") String activationLink)
            throws MessagingException, IOException, TemplateException {
        emailService.sendRegistrationEmail(username, emailAddress, activationLink);
        return ResponseEntity.ok(null);
    }
}
