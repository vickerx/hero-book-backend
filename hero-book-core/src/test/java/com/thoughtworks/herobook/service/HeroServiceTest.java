package com.thoughtworks.herobook.service;

import com.thoughtworks.herobook.dto.HeroDTO;
import com.thoughtworks.herobook.entity.Hero;
import com.thoughtworks.herobook.repository.HeroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {
    @Mock
    private HeroRepository heroRepository;
    @InjectMocks
    private HeroService heroService;

    @Test
    void should_create_hero() {
        Hero hero = Hero.builder().name("test").age(40).build();
        when(heroRepository.save(any())).thenReturn(hero);
        HeroDTO heroDTO = HeroDTO.builder().name("test").age(40).build();

        HeroDTO result = heroService.createHero(heroDTO);

        assertEquals("test", result.getName());
        assertEquals(40, result.getAge());
    }
}
