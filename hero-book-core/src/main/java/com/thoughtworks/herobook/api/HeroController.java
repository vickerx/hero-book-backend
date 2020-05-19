package com.thoughtworks.herobook.api;

import com.thoughtworks.herobook.dto.HeroDTO;
import com.thoughtworks.herobook.service.HeroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeroController {
    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @PostMapping
    public ResponseEntity<HeroDTO> createHero(@RequestBody HeroDTO hero) {
        return ResponseEntity.status(HttpStatus.CREATED).body(heroService.createHero(hero));
    }
}
