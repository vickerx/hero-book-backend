package com.thoughtworks.herobook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroStoryDetailDTO {
    private Long id;

    private String title;

    private String author;

    private String content;

    private Date createdTime;

    private Date updatedTime;
}
