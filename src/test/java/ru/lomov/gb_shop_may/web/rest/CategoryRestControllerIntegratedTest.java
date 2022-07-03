package ru.lomov.gb_shop_may.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.lomov.gb_shop_may.dao.CategoryDao;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO тесты
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryRestControllerIntegratedTest {

    public static final String SMARTPHONES = "Смартфоны";
    public static final String PERFUMERY = "Парфюмерия";
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryDao categoryDao;

    @Test
    @Order(2)
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(SMARTPHONES));
    }

    @Test
    @Order(1)
    void saveTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(
                                        CategoryDto.builder()
                                                .title(SMARTPHONES)
                                                .build()
                                )))
                .andExpect(status().isCreated());
        assertEquals(1, categoryDao.findAll().size());
    }

}