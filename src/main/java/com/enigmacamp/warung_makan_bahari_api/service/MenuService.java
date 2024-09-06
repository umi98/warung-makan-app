package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {
    Menu addMenu(Menu menu);
    Menu getMenuById(String id);
    List<Menu> getAllMenu(String name, Long minPrice, Long maxPrice);
    Menu updateMenu(Menu menu);
    void deleteMenu(String id);
}
