package com.thoughtworks.herobook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroStoryDetailDTO {
    private Long id;

    @Length(max = 200, message = "title's length must less than 200 characters")
    @NotBlank(message = "title must not be blank")
    private String title;

    private String author;

    private String email;

    @NotBlank(message = "content must not be blank")
    private String content;

    @Length(max = 300, message = "abstractContent's length must less than 300 characters")
    @NotBlank(message = "abstractContent must not be blank")
    private String abstractContent;

    private Date createdTime;

    private Date updatedTime;
}
