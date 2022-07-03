package ru.lomov.gb_shop_may.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.gb.gbapimay.category.dto.CategoryDto;
import ru.lomov.gb_shop_may.entity.Category;


@Mapper
public interface CategoryMapper {

    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}
