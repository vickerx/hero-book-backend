package com.thoughtworks.herobook.mapper;

import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.dto.HeroStoryDetailDTO;
import com.thoughtworks.herobook.entity.HeroStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class HeroStoryMapper {
    public static HeroStoryMapper HERO_STORY_MAPPER = Mappers.getMapper(HeroStoryMapper.class);

    @Mapping(target = "contentAbstract", expression = "java(calculateContentAbstract(heroStory))")
    public abstract HeroStoryDTO toDTO(HeroStory heroStory);

    public abstract HeroStoryDetailDTO toDetailDTO(HeroStory heroStory);

    public abstract HeroStory detailDTOtoEntity(HeroStoryDetailDTO heroStoryDetailDTO);

    String calculateContentAbstract(HeroStory heroStory) {
        final int maxSize = 297;
        return heroStory.getContent().length() > maxSize
                ? String.format("%s...", heroStory.getContent().substring(0, maxSize))
                : heroStory.getContent();
    }
}
