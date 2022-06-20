package ru.lomov.gb_shop_may.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.lomov.gb_shop_may.service.ManufacturerService;
import ru.lomov.gb_shop_may.web.dto.ManufacturerDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ManufacturerRestController.class)
class ManufacturerRestControllerMockMvcTest {
    public static final String APPLE = "Apple";
    public static final String HUGO = "Hugo";

    @MockBean
    ManufacturerService manufacturerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    List<ManufacturerDto> manufacturers;

    @BeforeEach
    void setUp() {
        manufacturers = new ArrayList<>();
        manufacturers.add(new ManufacturerDto(1L, APPLE));
        manufacturers.add(new ManufacturerDto(2L, HUGO));

    }

    @Test
    void findAll() throws Exception{
        Mockito.when(manufacturerService.findAll()).thenReturn(manufacturers); //Определение поведения сервиса

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value(APPLE))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].name").value(HUGO));
    }
@Test
    void saveTest() throws Exception {
    Mockito.when(manufacturerService.save(any(ManufacturerDto.class)))
            .thenReturn(ManufacturerDto.builder()
                    .id(1L)
                    .name(APPLE)
                    .build()); //Определение поведения сервиса

    mockMvc.perform(post("/api/v1/manufacturer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper
                            .writeValueAsString(
                                    ManufacturerDto.builder()
                                            .name(APPLE)
                                            .build()
                            )))
            .andExpect(status().isCreated());
}


}