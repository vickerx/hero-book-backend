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

    @NotBlank(message = "author must not be blank")
    private String author;

    @NotBlank(message = "content must not be blank")
    private String content;

    private Date createdTime;

    private Date updatedTime;
}
