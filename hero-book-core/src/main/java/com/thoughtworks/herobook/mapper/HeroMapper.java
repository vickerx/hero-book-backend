package com.thoughtworks.herobook.mapper;

import com.thoughtworks.herobook.dto.HeroDTO;
import com.thoughtworks.herobook.entity.Hero;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HeroMapper {
    HeroMapper HERO_MAPPER = Mappers.getMapper(HeroMapper.class);

    HeroDTO toDto(Hero hero);
}
