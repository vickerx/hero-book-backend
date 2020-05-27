package com.thoughtworks.herobook.api.mock;

import com.thoughtworks.herobook.entity.HeroStory;

import java.util.ArrayList;
import java.util.List;

public class HeroStoryMockData {
    public static List<HeroStory> mockList() {
        List<HeroStory> heroStories = new ArrayList<>();
        for (int i = 1; i <= 18; i++) {
            HeroStory heroStory = HeroStory.builder()
                    .title("Title " + i)
                    .author("Autohr " + i)
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
}
