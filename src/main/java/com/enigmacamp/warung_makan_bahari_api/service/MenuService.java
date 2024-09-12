package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MenuService {
    MenuResponse addMenu(MenuRequest menu);
    Menu getMenuById(String id);
    Page<Menu> getAllMenu(String name, Long minPrice, Long maxPrice, PagingRequest request);
    Menu updateMenu(Menu menu);
    void deleteMenu(String id);
}
