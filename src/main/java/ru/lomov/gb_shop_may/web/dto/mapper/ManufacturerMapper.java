package ru.lomov.gb_shop_may.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.gb.gbapimay.manufacturer.dto.ManufacturerDto;
import ru.lomov.gb_shop_may.entity.Manufacturer;


@Mapper
public interface ManufacturerMapper {

    Manufacturer toManufacturer(ManufacturerDto manufacturerDto);

    ManufacturerDto toManufacturerDto(Manufacturer manufacturer);
}
