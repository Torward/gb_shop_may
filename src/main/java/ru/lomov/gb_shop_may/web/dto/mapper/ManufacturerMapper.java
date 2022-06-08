package ru.lomov.gb_shop_may.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.lomov.gb_shop_may.entity.Manufacturer;
import ru.lomov.gb_shop_may.web.dto.ManufacturerDto;


@Mapper
public interface ManufacturerMapper {

    Manufacturer toManufacturer(ManufacturerDto manufacturerDto);

    ManufacturerDto toManufacturerDto(Manufacturer manufacturer);
}
