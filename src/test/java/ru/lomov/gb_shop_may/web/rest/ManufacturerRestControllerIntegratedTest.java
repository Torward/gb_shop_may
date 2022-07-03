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
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;
import ru.lomov.gb_shop_may.dao.ManufacturerDao;

import static org.hamcrest.Matchers.containsString;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManufacturerRestControllerIntegratedTest {
    public static final String APPLE = "Apple";
    public static final String HUGO = "Hugo";
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ManufacturerDao manufacturerDao;

    @Test
    @Order(2)
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value(APPLE));
    }

    @Test
    @Order(1)
    void saveTest() throws Exception {
        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(
                                        ManufacturerDto.builder()
                                                .name(APPLE)
                                                .build()
                                )))
                .andExpect(status().isCreated());
        assertEquals(1, manufacturerDao.findAll().size());
    }


}