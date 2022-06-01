package ru.lomov.gb_shop_may.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.lomov.gb_shop_may.dao.ManufacturerDao;
import ru.lomov.gb_shop_may.entity.Manufacturer;
import ru.lomov.gb_shop_may.web.dto.ManufacturerDto;
import ru.lomov.gb_shop_may.web.dto.mapper.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceMockitoTest {
    public static final String APPLE = "Apple";
    public static final String HUGO = "Hugo";

    @Mock
    ManufacturerDao manufacturerDao;

    @Mock
    ManufacturerMapper manufacturerMapper;

    @InjectMocks
    ManufacturerService manufacturerService;

    MockMvc mockMvc;

    List<Manufacturer> manufacturers;

    @BeforeEach
    void setUp(){
        manufacturers = new ArrayList<>();
        manufacturers.add(Manufacturer.builder()
                .id(1L)
                .name(APPLE)
                .build()
        );
        manufacturers.add(Manufacturer.builder()
                .id(2L)
                .name(HUGO)
                .build()
        );

    }

    @Test
    void findAllTest(){
        //given
        given(manufacturerDao.findAll()).willReturn(manufacturers);
        given(manufacturerMapper.toManufacturerDto(any())).will((invocation) -> {
            Manufacturer manufacturer = (Manufacturer) invocation.getArgument(0);
            if(manufacturer == null){
                return null;
            }
            return ManufacturerDto.builder()
                    .id(manufacturer.getId())
                    .name(manufacturer.getName())
                    .build();
        });

        final int size = manufacturers.size();
        //when
        List<ManufacturerDto> manufacturerDtoList = manufacturerService.findAll();
        //then
        then(manufacturerDao).should().findAll();

        assertAll(
                ()-> assertEquals(size, manufacturerDtoList.size(), "Size, must be an equal of "+ size),
                ()-> assertEquals(APPLE, manufacturerDtoList.get(0).getName())
        );
    }

    /*
     * TODO сохранение, удаление. Этот комментарий перед сдачей удалить.
     * */
}

