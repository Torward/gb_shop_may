package ru.lomov.gb_shop_may.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lomov.gb_shop_may.entity.Category;
import ru.lomov.gb_shop_may.entity.Manufacturer;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findByTitleLike(String title);
    Optional<Category> findByTitle(String name);
}
