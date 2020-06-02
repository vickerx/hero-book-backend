package com.thoughtworks.herobook.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Length(min = 1, max = 20, message = "Username only allows 1-20 characters")
    private String username;
    @Pattern(regexp = "^[\\x21-\\x7e]{8,30}$", message = "Password only allows 8-20 ASCII characters")
    private String password;
    @Email(message = "Please enter the correct email")
    private String email;
}
