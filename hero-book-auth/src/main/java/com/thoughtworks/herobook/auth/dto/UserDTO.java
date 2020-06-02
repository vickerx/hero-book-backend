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
    @Length(min = 1, max = 20, message = "昵称仅允许1-20个字符")
    private String username;
    @Pattern(regexp = "^[\\x21-\\x7e]{8,30}$", message = "密码仅允许8-30个ASCII字符")
    private String password;
    @Email(message = "请输入正确的邮箱格式")
    private String email;
}
