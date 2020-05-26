package com.thoughtworks.herobook.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailType {
    USER_REGISTRATION("Hero Stories帐户激活", "user-registration.ftl");

    private String subject;
    private String templateName;
}
