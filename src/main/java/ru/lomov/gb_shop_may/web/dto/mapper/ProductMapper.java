package ru.lomov.gb_shop_may.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lomov.gb_shop_may.dao.CategoryDao;
import ru.lomov.gb_shop_may.dao.ManufacturerDao;
import ru.lomov.gb_shop_may.entity.Category;
import ru.lomov.gb_shop_may.entity.Manufacturer;
import ru.lomov.gb_shop_may.entity.Product;
import ru.lomov.gb_shop_may.web.dto.CategoryDto;
import ru.lomov.gb_shop_may.web.dto.ProductDto;
import ru.lomov.gb_shop_may.web.dto.ProductManufacturerDto;


import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    ProductDto toProductDto(Product product);

    @Mapping(source = "manufacturer", target = "manufacturerDto")
    ProductManufacturerDto toProductManufacturerDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    default Set<Category> toCategory(Set<CategoryDto>categories, @Context CategoryDao categoryDao){
        return categories.stream().map(
                c -> categoryDao.findById(c.getId()).orElseThrow(NoSuchElementException::new)
        ).collect(Collectors.toSet());
    }
}
