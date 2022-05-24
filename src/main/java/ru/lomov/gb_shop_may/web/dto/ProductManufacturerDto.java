package ru.lomov.gb_shop_may.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.lomov.gb_shop_may.entity.enums.Status;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductManufacturerDto {
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal cost;
    @PastOrPresent
    private LocalDate manufactureDate;
    @NotNull
    private Status status;
    @JsonProperty(value = "manufacturer")
    private ManufacturerDto manufacturerDto;
}