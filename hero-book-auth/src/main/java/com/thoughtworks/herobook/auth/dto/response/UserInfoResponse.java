package com.thoughtworks.herobook.auth.dto.response;

import com.thoughtworks.herobook.auth.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {
    private Long id;

    private String username;

    private String email;

    public static UserInfoResponse of(User user) {
        if (user == null) {
            return null;
        }
        return UserInfoResponse
                .builder().id(user.getId()).email(user.getEmail()).username(user.getUsername()).build();
    }
}
