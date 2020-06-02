package com.thoughtworks.herobook.service;

import com.thoughtworks.herobook.common.exception.ResourceNotFoundException;
import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.dto.HeroStoryDetailDTO;
import com.thoughtworks.herobook.entity.HeroStory;
import com.thoughtworks.herobook.mapper.HeroStoryMapper;
import com.thoughtworks.herobook.repository.HeroStoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeroStoryService {
    private final HeroStoryRepository heroStoryRepository;

    public HeroStoryDetailDTO getHeroStoryDetailById(Long id) {
        HeroStory heroStory = heroStoryRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Can not find Hero Story by id :" + id));
        return HeroStoryMapper.HERO_STORY_MAPPER.toDetailDTO(heroStory);
    }

    public Page<HeroStoryDTO> getHeroStoriesByPage(Pageable pageable) {
        return heroStoryRepository.findAllByOrderByUpdatedTimeDesc(pageable)
                .map(HeroStoryMapper.HERO_STORY_MAPPER::toDTO);
    }

    @Transactional
    public void createHeroStory(HeroStoryDetailDTO detailDTO) {
        heroStoryRepository.save(HeroStoryMapper.HERO_STORY_MAPPER.detailDTOtoEntity(detailDTO));
    }
}
