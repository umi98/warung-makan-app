package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MenuService {
    MenuResponse addMenu(MenuRequest menu);
    MenuResponse getMenuById(String id);
    Menu getById(String id);
    Page<MenuResponse> getAllMenu(String name, Long minPrice, Long maxPrice, PagingRequest request);
    Resource getMenuImageById(String id);
    MenuResponse updateMenu(String id, MenuRequest menu);
    void deleteMenu(String id);
}
