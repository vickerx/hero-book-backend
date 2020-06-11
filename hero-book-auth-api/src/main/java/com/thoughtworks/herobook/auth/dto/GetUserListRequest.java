package com.thoughtworks.herobook.auth.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserListRequest {
    List<String> emails;
}
