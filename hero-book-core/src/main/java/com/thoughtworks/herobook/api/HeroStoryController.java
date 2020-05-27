package com.thoughtworks.herobook.api;

import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.service.HeroStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/hero-story")
public class HeroStoryController {
    @Autowired
    HeroStoryService heroStoryService;

    @GetMapping(params = "page")
    public Page<HeroStoryDTO> getHeroStoriesByPage(Pageable pageable) {
        return this.heroStoryService.getHeroStoriesByPage(pageable);
    }
}
