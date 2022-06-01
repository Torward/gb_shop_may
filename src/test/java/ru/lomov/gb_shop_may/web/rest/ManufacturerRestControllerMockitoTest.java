package ru.lomov.gb_shop_may.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.lomov.gb_shop_may.service.ManufacturerService;
import ru.lomov.gb_shop_may.web.dto.ManufacturerDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerRestControllerMockitoTest {
    public static final String APPLE = "Apple";
    public static final String HUGO = "Hugo";


    @Mock
    ManufacturerService manufacturerService;

    @InjectMocks
    ManufacturerRestController manufacturerRestController;
    MockMvc mockMvc;
    List<ManufacturerDto> manufacturers;

    @BeforeEach
    void setUp(){
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
    void getManufacturerListTest(){
        // given
        given(manufacturerService.findAll()).willReturn(manufacturers);
        final int size = manufacturers.size();
        // when
        List<ManufacturerDto> manufacturerList = manufacturerRestController.getManufacturerList();
        // then
        then(manufacturerService).should().findAll();

        assertAll(
                ()-> assertEquals(size, manufacturerList.size(), "Size, must be an equal of "+ size),
                ()-> assertEquals(APPLE, manufacturerList.get(0).getName())
        );
    }
/*
* TODO сохранение, удаление. Обычными и mockMvc (4 теста) Этот комментарий перед сдачей удалить.
* */
//@Test
//void postManufacturerMockMvcTest() throws Exception {
//    // given
//    given(manufacturerService.findAll()).willReturn(manufacturers);
//
//    mockMvc.perform(post("/api/v1/manufacturer"))
//            .andExpect(status().isOk())
//            .andExpect(content().string(containsString("id")))
//            .andExpect(jsonPath("$.[0].id").value("1"))
//            .andExpect(jsonPath("$.[0].name").value(APPLE))
//            .andExpect(jsonPath("$.[1].id").value("2"))
//            .andExpect(jsonPath("$.[1].name").value(HUGO));
//}
}