package ru.lomov.gb_shop_may.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVE("Доступно"), DISABLED("Недоступно");

    private final String title;
}
