package com.thoughtworks.herobook.api.hero;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.herobook.api.common.BaseControllerTest;
import com.thoughtworks.herobook.api.mock.HeroStoryMockData;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import com.thoughtworks.herobook.entity.HeroStory;
import com.thoughtworks.herobook.repository.HeroStoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HeroStoryControllerTest extends BaseControllerTest {
    private static ObjectMapper objectMapper;

    @Autowired
    HeroStoryRepository heroStoryRepository;
    private HeroStory mockHeroStory;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void beforeEach() {
        List<HeroStory> heroStories = HeroStoryMockData.mockList();
        this.heroStoryRepository.saveAll(heroStories);

        this.mockHeroStory = HeroStoryMockData.mock();
        this.heroStoryRepository.save(this.mockHeroStory);
        assertNotNull(this.mockHeroStory.getId());
    }

    @Test
    void should_post_hero_story_success() throws Exception {
        HeroStory heroStory = HeroStoryMockData.mock();
        mockMvc.perform(post("/hero-story")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(heroStory)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_create_hero_story_failed_when_title_is_empty() throws Exception {
        HeroStory heroStory = HeroStoryMockData.mock();
        heroStory.setTitle("");
        mockMvc.perform(post("/hero-story")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(heroStory)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("title's length must between 1 and 200 characters")))
                .andExpect(jsonPath("$.error_code", is(ErrorCode.INVALIDATE_PARAMS.getValue())));
    }

    @Test
    void should_create_hero_story_failed_when_title_over_200() throws Exception {
        HeroStory heroStory = HeroStoryMockData.mock();
        heroStory.setTitle("" +
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1");
        mockMvc.perform(post("/hero-story")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(heroStory)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("title's length must between 1 and 200 characters")))
                .andExpect(jsonPath("$.error_code", is(ErrorCode.INVALIDATE_PARAMS.getValue())));
    }

    @Test
    void should_create_hero_story_failed_when_content_is_blank() throws Exception {
        HeroStory heroStory = HeroStoryMockData.mock();
        heroStory.setContent("");
        mockMvc.perform(post("/hero-story")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(heroStory)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("content must not be blank")))
                .andExpect(jsonPath("$.error_code", is(ErrorCode.INVALIDATE_PARAMS.getValue())));
    }

    @Test
    void should_get_hero_story_by_id() throws Exception {
        mockMvc.perform(get("/hero-story/open/" + this.mockHeroStory.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.author", is("Author")))
                .andExpect(jsonPath("$.content", is("Content")));
    }

    @Test
    void should_get_hero_story_pageable() throws Exception {
        mockMvc.perform(get("/hero-story/open")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "7")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is(7)))
                .andExpect(jsonPath("$.totalPages", is(4)))
                .andExpect(jsonPath("$.totalElements", is(19)));
    }
}
