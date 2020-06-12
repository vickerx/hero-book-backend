package com.thoughtworks.herobook.service;

import com.thoughtworks.herobook.api.mock.HeroStoryMockData;
import com.thoughtworks.herobook.client.UserApiClient;
import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.entity.HeroStory;
import com.thoughtworks.herobook.repository.HeroStoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeroStoryServiceTest {
    @Mock
    private HeroStoryRepository heroStoryRepository;

    @Mock
    private UserApiClient userApiClient;

    @InjectMocks
    private HeroStoryService heroStoryService;

    @Test
    void should_get_hero_story_pageable() {
        Page<HeroStory> page = new PageImpl<>(HeroStoryMockData.mockList());
        when(heroStoryRepository.findAllByOrderByUpdatedTimeDesc(any())).thenReturn(page);

        when(userApiClient.getUserByEmails(any())).thenReturn(HeroStoryMockData.mockUserList());

        Page<HeroStoryDTO> response = heroStoryService.getHeroStoriesByPage(PageRequest.of(1, 5));

        response.forEach(heroStoryDTO ->
                assertTrue(heroStoryDTO.getContentAbstract().length() <= 300));
    }

}
