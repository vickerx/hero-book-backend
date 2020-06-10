package com.thoughtworks.herobook.api;

import com.thoughtworks.herobook.common.exception.ParamsValidateException;
import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.dto.HeroStoryDetailDTO;
import com.thoughtworks.herobook.exception.UploadImageException;
import com.thoughtworks.herobook.service.HeroStoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequestMapping("/hero-story")
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
            throw new ParamsValidateException(bindingResult.getFieldError().getDefaultMessage());
        }
        heroStoryService.createHeroStory(detailDTO);
    }

    @PutMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new UploadImageException("image is empty");
        }
        String uuid = heroStoryService.uploadImage(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body(uuid);
    }

    @GetMapping("/image/{uuid}")
    public ResponseEntity<Resource> getImage(@PathVariable("uuid") String uuid) {
        InputStreamResource imageStream = heroStoryService.getImage(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(imageStream);
    }
}
