package com.thoughtworks.herobook.mapper;

import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.entity.HeroStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class HeroStoryMapper {
    public static HeroStoryMapper HERO_STORY_MAPPER = Mappers.getMapper(HeroStoryMapper.class);

    @Mapping(target = "contentAbstract", expression = "java(calculateContentAbstract(heroStory.getContent()))")
    public abstract HeroStoryDTO toDTO(HeroStory heroStory);

    String calculateContentAbstract(String content) {
        final int maxSize = 300;
        return content.length() > maxSize ? content.substring(0, maxSize) : content;
    }
}
