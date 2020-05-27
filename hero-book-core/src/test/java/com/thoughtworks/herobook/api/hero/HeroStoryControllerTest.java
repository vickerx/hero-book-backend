package com.thoughtworks.herobook.api.hero;

import com.thoughtworks.herobook.api.common.BaseControllerTest;
import com.thoughtworks.herobook.api.mock.HeroStoryMockData;
import com.thoughtworks.herobook.entity.HeroStory;
import com.thoughtworks.herobook.repository.HeroStoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HeroStoryControllerTest extends BaseControllerTest {
    @Autowired
    HeroStoryRepository heroStoryRepository;

    @BeforeEach
    void beforeEach() {
        List<HeroStory> heroStories = HeroStoryMockData.mockList();
        this.heroStoryRepository.saveAll(heroStories);
    }

    @Test
    void should_get_hero_story_pageable() throws Exception {
        mockMvc.perform(get("/api/hero-story")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(4)))
                .andExpect(jsonPath("$.totalElements", is(18)));
    }
}
