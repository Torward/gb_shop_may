package ru.lomov.gb_shop_may.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.lomov.gb_shop_may.config.ShopConfig;
import ru.lomov.gb_shop_may.entity.Manufacturer;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Import(ShopConfig.class)
class ManufacturerDaoDataJpaTest {
    public static final String APPLE = "Apple";
    public static final String HUGO = "Hugo";
    @Autowired
    ManufacturerDao manufacturerDao;

    @Test
    public void findAllTest(){

    }
    @Test
    public void saveTest(){
        Manufacturer manufacturer = Manufacturer.builder()
                .name(APPLE)
                .build();
        Manufacturer savedManufacturer = manufacturerDao.save(manufacturer);
        assertAll(
                ()-> assertEquals(1L, savedManufacturer.getId()),
                ()-> assertEquals(APPLE, savedManufacturer.getName()),
                ()-> assertEquals(0, savedManufacturer.getVersion()),
                ()-> assertEquals("User", savedManufacturer.getCreatedBy()),
                ()-> assertNotNull(savedManufacturer.getCreatedDate()),
                ()-> assertEquals("User", savedManufacturer.getLastModifiedBy()),
                ()-> assertNotNull( savedManufacturer.getLastModifiedDate())
        );
    }
// TODO тест findAll, delete, update
}