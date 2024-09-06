package com.enigmacamp.warung_makan_bahari_api.repository;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findAllByNameLikeIgnoreCaseAndPriceBetween(String name, Long minPrice, Long maxPrice);
    List<Menu> findAllByPriceBetween(Long minPrice, Long maxPrice);
}
