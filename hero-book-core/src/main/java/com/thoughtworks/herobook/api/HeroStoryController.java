package com.thoughtworks.herobook.api;

import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.dto.HeroStoryDetailDTO;
import com.thoughtworks.herobook.service.HeroStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;


@RestController
@RequestMapping("/api/hero-story")
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeroStoryController {

    private final HeroStoryService heroStoryService;

    @GetMapping("/{id}")
    public HeroStoryDetailDTO getHeroStoryById(@PathVariable Long id) {
        return heroStoryService.getHeroStoryDetailById(id);
    }

    @GetMapping(params = "page")
    public Page<HeroStoryDTO> getHeroStoriesByPage(Pageable pageable) {
        return heroStoryService.getHeroStoriesByPage(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHeroStory(@RequestBody @Valid HeroStoryDetailDTO detailDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
        heroStoryService.createHeroStory(detailDTO);
    }
}
