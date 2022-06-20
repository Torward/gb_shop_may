package ru.lomov.gb_shop_may.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.lomov.gb_shop_may.entity.Category;
import ru.lomov.gb_shop_may.entity.Manufacturer;
import ru.lomov.gb_shop_may.web.dto.CategoryDto;
import ru.lomov.gb_shop_may.web.dto.ManufacturerDto;


@Mapper
public interface CategoryMapper {

    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}
