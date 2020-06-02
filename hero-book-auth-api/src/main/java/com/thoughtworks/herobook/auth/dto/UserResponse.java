package com.thoughtworks.herobook.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@Builder
public class UserResponse {
    private Long id;

    private String username;

    private String password;

    private String email;

    private Boolean isActivated;
}
