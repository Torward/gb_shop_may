package ru.lomov.gb_shop_may.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import ru.lomov.gb_shop_may.dao.ManufacturerDao;
import ru.lomov.gb_shop_may.entity.Manufacturer;
import ru.lomov.gb_shop_may.web.dto.ManufacturerDto;
import ru.lomov.gb_shop_may.web.dto.mapper.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/*
* Выполнил, работает.
* */
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


    @Test
    void saveTest() {
        // given
        ManufacturerDto testDto = ManufacturerDto.builder()
                .id(1L)
                .name("test")
                .build();
        given(manufacturerMapper.toManufacturer(any())).will((Answer<Manufacturer>) invocation -> {
            ManufacturerDto manufacturerDto = (ManufacturerDto) invocation.getArgument(0);

            if (manufacturerDto == null){
                return null;
            }

            return Manufacturer.builder()
                    .id(manufacturerDto.getId())
                    .name(manufacturerDto.getName())
                    .build();
        });
        given(manufacturerMapper.toManufacturerDto(any())).will((Answer<ManufacturerDto>) invocation -> {
            Manufacturer manufacturer = (Manufacturer) invocation.getArgument(0);

            if (manufacturer == null){
                return null;
            }

            return ManufacturerDto.builder()
                    .id(manufacturer.getId())
                    .name(manufacturer.getName())
                    .build();

        });
        // when
        manufacturerService.save(testDto);
        // then
        then(manufacturerDao).should().findById(1L);
        then(manufacturerDao).should().save(any());
    }
    @Test
    void deleteTest() {
        //when
        manufacturerService.deleteById(1L);
        //then
        then(manufacturerDao).should().deleteById(1L);
    }

}

