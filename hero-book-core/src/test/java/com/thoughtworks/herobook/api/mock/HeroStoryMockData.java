package com.thoughtworks.herobook.api.mock;

import com.thoughtworks.herobook.auth.dto.UserResponse;
import com.thoughtworks.herobook.entity.HeroStory;

import java.util.ArrayList;
import java.util.List;

public class HeroStoryMockData {
    public static List<HeroStory> mockList() {
        List<HeroStory> heroStories = new ArrayList<>();
        for (int i = 1; i <= 18; i++) {
            HeroStory heroStory = HeroStory.builder()
                    .title("Title " + i)
                    .email("email@email.com " + i)
                    .html("<html></html>")
                    .content("Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " +
                            "Content Content Content Content Content Content Content Content Content Content " + i)
                    .build();
            heroStories.add(heroStory);
        }
        return heroStories;
    }

    public static HeroStory mock() {
        return HeroStory.builder()
                .title("Title")
                .email("email@email.com")
                .html("<html></html>")
                .content("Content")
                .build();
    }

    public static List<UserResponse> mockUserList() {
        List<UserResponse> userList = new ArrayList<>();
        for (int i = 1; i <= 18; i++) {
            UserResponse userResponse = UserResponse.builder()
                    .email("email@email.com " + i)
                    .username("Author " + i)
                    .isActivated(true)
                    .build();
            userList.add(userResponse);
        }
        return userList;
    }
}
