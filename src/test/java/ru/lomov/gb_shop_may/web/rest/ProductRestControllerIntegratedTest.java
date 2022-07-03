package ru.lomov.gb_shop_may.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapimay.common.enums.Status;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.lomov.gb_shop_may.dao.CategoryDao;
import ru.lomov.gb_shop_may.dao.ManufacturerDao;
import ru.lomov.gb_shop_may.dao.ProductDao;
import ru.lomov.gb_shop_may.entity.Category;
import ru.lomov.gb_shop_may.entity.Manufacturer;
import ru.lomov.gb_shop_may.web.dto.mapper.CategoryMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//TODO тесты

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class ProductRestControllerIntegratedTest {
    public static final String APPLE_COMPANY_NAME = "Apple";
    public static final String SMARTPHONES = "Смартфоны";
    public static final String APPLE_SMARTPHONE = "Apple_13_X";
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ManufacturerDao manufacturerDao;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductDao productDao;

    @Test
    @Order(2)
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].cost").value("72000.0"))
                .andExpect(jsonPath("$.[0].title").value(APPLE_SMARTPHONE));
    }

    @Test
    @Order(1)
    void saveTest() throws Exception {
        Category savedCategory = categoryDao.save(
                Category.builder()
                        .title(SMARTPHONES)
                        .build()
        );
        Manufacturer savedManufacturer = manufacturerDao.save(
                Manufacturer.builder()
                        .name(APPLE_COMPANY_NAME)
                        .build()
        );
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(
                                        ProductDto.builder()
                                                .title(APPLE_SMARTPHONE)
                                                .cost(new BigDecimal(72000))
                                                .status(Status.ACTIVE)
                                                .manufactureDate(LocalDate.now())
                                                .manufacturer(APPLE_COMPANY_NAME)
                                                .categories(Set.of(categoryMapper.toCategoryDto(savedCategory)))
                                                .build()
                                )))
                .andExpect(status().isCreated());
        assertEquals(1, productDao.findAll().size());
    }
}