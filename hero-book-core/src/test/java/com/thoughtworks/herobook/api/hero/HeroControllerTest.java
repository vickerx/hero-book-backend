package com.thoughtworks.herobook.api.hero;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.herobook.api.common.BaseControllerTest;
import com.thoughtworks.herobook.entity.Hero;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HeroControllerTest extends BaseControllerTest {
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void should_create_hero() throws Exception {
        Hero hero = Hero.builder().name("test").age(33).build();
        mockMvc.perform(post("/api/hero")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hero)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.age", is(33)));
    }
}
