package com.thoughtworks.herobook.auth.dto.response;

import com.thoughtworks.herobook.auth.entity.User;
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

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .isActivated(user.getIsActivated())
                .username(user.getUsername())
                .build();
    }
}
