package com.thoughtworks.herobook.service;

import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.mapper.HeroStoryMapper;
import com.thoughtworks.herobook.repository.HeroStoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeroStoryService {
    private final HeroStoryRepository heroStoryRepository;

    public Page<HeroStoryDTO> getHeroStoriesByPage(Pageable pageable) {
        return heroStoryRepository.findAllByOrderByUpdatedTimeDesc(pageable)
                .map(HeroStoryMapper.HERO_STORY_MAPPER::toDTO);
    }
}
