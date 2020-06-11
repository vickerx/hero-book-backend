package com.thoughtworks.herobook.service;

import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.client.UserApiClient;
import com.thoughtworks.herobook.common.exception.ResourceNotFoundException;
import com.thoughtworks.herobook.dto.HeroStoryDTO;
import com.thoughtworks.herobook.dto.HeroStoryDetailDTO;
import com.thoughtworks.herobook.entity.HeroStory;
import com.thoughtworks.herobook.exception.DownloadImageException;
import com.thoughtworks.herobook.exception.UploadImageException;
import com.thoughtworks.herobook.mapper.HeroStoryMapper;
import com.thoughtworks.herobook.repository.HeroStoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class HeroStoryService {

    @Value("${img.location}")
    private String location;

    private final UserApiClient userApiClient;

    private final HeroStoryRepository heroStoryRepository;

    public HeroStoryDetailDTO getHeroStoryDetailById(Long id) {
        HeroStory heroStory = heroStoryRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Can not find Hero Story by id :" + id));

        Map<String, UserResponse> userMap = getUserMap(Collections.singletonList(heroStory.getEmail()));
        return HeroStoryMapper.HERO_STORY_MAPPER.toDetailDTO(heroStory, userMap);
    }

    public Page<HeroStoryDTO> getHeroStoriesByPage(Pageable pageable) {
        Page<HeroStory> page = heroStoryRepository.findAllByOrderByUpdatedTimeDesc(pageable);
        List<String> emails = page.stream().map(HeroStory::getEmail).collect(Collectors.toList());
        Map<String, UserResponse> userMap = getUserMap(emails);
        return page.map(heroStory -> HeroStoryMapper.HERO_STORY_MAPPER.toDTO(heroStory, userMap));
    }

    @Transactional
    public void createHeroStory(HeroStoryDetailDTO detailDTO) {
        heroStoryRepository.save(HeroStoryMapper.HERO_STORY_MAPPER.detailDTOtoEntity(detailDTO));
    }

    public String uploadImage(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        String fileName = multipartFile.getOriginalFilename();
        log.info("upload image:name={},type={}", fileName, contentType);
        String uuid = UUID.randomUUID().toString();
        try {
            saveImage(multipartFile, uuid);
            return uuid;
        } catch (IOException e) {
            throw new UploadImageException("save image error: " + e.getMessage());
        }
    }

    public InputStreamResource getImage(String uuid) {
        String filePath = location + uuid;
        Path path = Paths.get(filePath);
        try {
            return new InputStreamResource(Files.newInputStream(path));
        } catch (IOException e) {
            throw new DownloadImageException("download image error: " + e.getMessage());
        }
    }

    private void saveImage(MultipartFile multipartFile, String uuid) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(location, uuid);
        Files.write(path, bytes);
    }

    private Map<String, UserResponse> getUserMap(List<String> emails) {
        List<UserResponse> users = userApiClient.getUserByEmails(emails);
        return users.stream().collect(Collectors.toMap(UserResponse::getEmail, Function.identity()));
    }

}
