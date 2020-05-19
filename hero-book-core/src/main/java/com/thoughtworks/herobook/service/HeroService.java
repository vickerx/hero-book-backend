package com.thoughtworks.herobook.service;

import com.thoughtworks.herobook.dto.HeroDTO;
import com.thoughtworks.herobook.entity.Hero;
import com.thoughtworks.herobook.mapper.HeroMapper;
import com.thoughtworks.herobook.repository.HeroRepository;
import org.springframework.stereotype.Service;

@Service
public class HeroService {
    private final HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public HeroDTO createHero(HeroDTO heroDTO) {
        Hero hero = Hero.builder().name(heroDTO.getName()).age(heroDTO.getAge()).build();
        Hero savedHero = heroRepository.save(hero);
        return HeroMapper.HERO_MAPPER.toDto(savedHero);
    }
}
