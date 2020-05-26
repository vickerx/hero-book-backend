package com.thoughtworks.herobook.email.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class EmailVO {
    private String templateName;
    private String subject;
    private String targetEmailAddress;
    private Map model;
}
