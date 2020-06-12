package com.thoughtworks.herobook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroStoryDTO {
    private Long id;

    private String title;

    private String author;

    private String abstractContent;

    private Date createdTime;

    private Date updatedTime;
}
