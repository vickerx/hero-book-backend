package com.thoughtworks.herobook.mapper;

import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.dto.HeroStoryDetailDTO;
import com.thoughtworks.herobook.entity.HeroStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public abstract class HeroStoryMapper {
    public static HeroStoryMapper HERO_STORY_MAPPER = Mappers.getMapper(HeroStoryMapper.class);

    @Mapping(target = "contentAbstract", expression = "java(calculateContentAbstract(heroStory))")
    @Mapping(target = "author", expression = "java(getAuthor(heroStory, user))")
    public abstract HeroStoryDTO toDTO(HeroStory heroStory, Map<String, UserResponse> user);

    @Mapping(target = "author", expression = "java(getAuthor(heroStory, user))")
    public abstract HeroStoryDetailDTO toDetailDTO(HeroStory heroStory, Map<String, UserResponse> user);

    public abstract HeroStory detailDTOtoEntity(HeroStoryDetailDTO heroStoryDetailDTO);

    String calculateContentAbstract(HeroStory heroStory) {
        final int maxSize = 297;
        return heroStory.getContent().length() > maxSize
                ? String.format("%s...", heroStory.getContent().substring(0, maxSize))
                : heroStory.getContent();
    }

    String getAuthor(HeroStory heroStory, Map<String, UserResponse> user) {
        String author = "";
        if (user.containsKey(heroStory.getEmail())) {
            author = user.get(heroStory.getEmail()).getUsername();
        }
        return author;
    }
}
