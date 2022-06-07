package ru.lomov.gb_shop_may.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.lomov.gb_shop_may.config.ShopConfig;
import ru.lomov.gb_shop_may.entity.Manufacturer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ShopConfig.class)
class ManufacturerDaoDataJpaTest {
    public static final String APPLE = "Apple";
    public static final String HUGO = "Hugo";
    @Autowired
    ManufacturerDao manufacturerDao;
    List<Manufacturer> manufacturers;

    @Test
    public void findAllTest(){
        Manufacturer manufacturer = Manufacturer.builder()
                .name(APPLE)
                .build();
        manufacturerDao.save(manufacturer);
        final int size = manufacturerDao.findAll().size();
        List<Manufacturer> manufacturerList = manufacturerDao.findAll();
        System.out.println(manufacturerList);

        assertAll(
                ()-> assertEquals(size, manufacturerList.size(), "Size, must be an equal of "+ size),
                ()-> assertEquals(APPLE, manufacturerList.get(0).getName())
        );
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

    @Test
    public void deleteTest(){
        boolean isDeleted;
        Manufacturer manufacturer = Manufacturer.builder()
                .name(APPLE)
                .build();
        manufacturerDao.save(manufacturer);

        manufacturerDao.delete(manufacturer);

        Optional<Manufacturer> savedManufacturer = manufacturerDao.findById(1L);

        isDeleted = savedManufacturer.isEmpty();
        assertTrue(isDeleted);

    }
    @Test
    public void updateTest(){
        Manufacturer manufacturer = Manufacturer.builder()
                .name(APPLE)
                .build();
        manufacturerDao.save(manufacturer);
        Manufacturer savedManufacturer = manufacturerDao.findById(1L).get();
       savedManufacturer.setCreatedBy("Admin");
        assertEquals("Admin", savedManufacturer.getCreatedBy());
    }
}