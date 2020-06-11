package com.thoughtworks.herobook.mapper;

import com.thoughtworks.herobook.api.mock.HeroStoryMockData;
import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.dto.HeroStoryDetailDTO;
import com.thoughtworks.herobook.entity.HeroStory;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroStoryMapperTest {

    @Test
    void should_mapper_to_dto_success() {
        HeroStory heroStory = HeroStoryMockData.mock();
        Map<String, UserResponse> userMap = new ConcurrentHashMap<>();
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .email(heroStory.getEmail())
                .isActivated(true)
                .username("Author")
                .password("123456")
                .build();

        userMap.put(userResponse.getEmail(), userResponse);

        HeroStoryDTO dto = HeroStoryMapper.HERO_STORY_MAPPER.toDTO(heroStory, userMap);
        assertEquals(userResponse.getUsername(), dto.getAuthor());
    }

    @Test
    void should_mapper_to_detail_dto_success() {
        HeroStory heroStory = HeroStoryMockData.mock();
        Map<String, UserResponse> userMap = new ConcurrentHashMap<>();
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .email(heroStory.getEmail())
                .isActivated(true)
                .username("Author")
                .password("123456")
                .build();

        userMap.put(userResponse.getEmail(), userResponse);

        HeroStoryDetailDTO dto = HeroStoryMapper.HERO_STORY_MAPPER.toDetailDTO(heroStory, userMap);
        assertEquals(userResponse.getUsername(), dto.getAuthor());
    }
}
