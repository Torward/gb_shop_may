package ru.lomov.gb_shop_may.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;
import ru.lomov.gb_shop_may.service.ManufacturerService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
* Выполнено
* */

@ExtendWith(MockitoExtension.class)
class ManufacturerRestControllerMockitoTest {
    public static final String APPLE = "Apple";
    public static final String HUGO = "Hugo";


    @Mock
    ManufacturerService manufacturerService;

    @InjectMocks
    ManufacturerRestController manufacturerRestController;
    @Mock
    ObjectMapper objectMapper;
    MockMvc mockMvc;
    List<ManufacturerDto> manufacturers;

    @BeforeEach
    void setUp() {
        manufacturers = new ArrayList<>();
        manufacturers.add(new ManufacturerDto(1L, APPLE));
        manufacturers.add(new ManufacturerDto(2L, HUGO));
        mockMvc = MockMvcBuilders.standaloneSetup(manufacturerRestController).build();
    }

    @Test
    void getManufacturerListMockMvcTest() throws Exception {
        // given
        given(manufacturerService.findAll()).willReturn(manufacturers);

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value(APPLE))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].name").value(HUGO));
    }

    @Test
    void getManufacturerListTest() {
        // given
        given(manufacturerService.findAll()).willReturn(manufacturers);
        final int size = manufacturers.size();
        // when
        List<ManufacturerDto> manufacturerList = manufacturerRestController.getManufacturerList();
        // then
        then(manufacturerService).should().findAll();

        assertAll(
                () -> assertEquals(size, manufacturerList.size(), "Size, must be an equal of " + size),
                () -> assertEquals(APPLE, manufacturerList.get(0).getName())
        );
    }
    @Test
    void postManufacturerMockMvcTest() throws Exception {
        given(manufacturerService.save(ArgumentMatchers.any())).will((invocation) -> {
            ManufacturerDto manufacturerDto = invocation.getArgument(0);
            return ManufacturerDto.builder()
                    .id(manufacturerDto.getId())
                    .name(manufacturerDto.getName())
                    .build();
        });

        mockMvc.perform(post("/api/v1/manufacturer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"test\"}"))
                .andExpect(status().isCreated());

    }

    @Test
    void deleteManufacturerMockMvcTest() throws Exception {
        mockMvc.perform(delete("/api/v1/manufacturer/{manufacturerId}", 1))
                .andExpect(status().isNoContent());

        given(manufacturerService.save(ArgumentMatchers.any())).will((invocation) -> {
            ManufacturerDto manufacturerDto = invocation.getArgument(0);
            return ManufacturerDto.builder()
                    .id(manufacturerDto.getId())
                    .name(manufacturerDto.getName())
                    .build();
        });

        mockMvc.perform(post("/api/v1/manufacturer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"test\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/v1/manufacturer/{manufacturerId}", 1))
                .andExpect(status().isNoContent());

    }
    @Test
    void saveTest() {
        ManufacturerDto testDto = ManufacturerDto.builder()
                .id(1L)
                .name("test")
                .build();
        // given
        given(manufacturerService.save(ArgumentMatchers.any())).will((invocation) -> {
            ManufacturerDto manufacturerDto = invocation.getArgument(0);
            return ManufacturerDto.builder()
                    .id(manufacturerDto.getId())
                    .name(manufacturerDto.getName())
                    .build();
        });
        // when
        manufacturerRestController.handlePost(testDto);
        // then
        then(manufacturerService).should().save(ArgumentMatchers.any());
    }
    @Test
    void deleteTest() {
        // when
        manufacturerRestController.deleteById(1L);
        // then
        then(manufacturerService).should().deleteById(1L);
    }
}