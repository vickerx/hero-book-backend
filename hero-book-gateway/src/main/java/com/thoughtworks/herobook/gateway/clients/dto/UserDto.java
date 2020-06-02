package com.thoughtworks.herobook.gateway.clients.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
@Setter(AccessLevel.NONE)
public class UserDto {
    private String email;
    private String password;

}
