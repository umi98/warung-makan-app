package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenuById(String id) {
        return findByIdOrThrowException(id);
    }

    @Override
    public List<Menu> getAllMenu(String name, Long minPrice, Long maxPrice) {
        if (name == null && minPrice == null && maxPrice == null) {
            return menuRepository.findAll();
        } else if (name == null) {
            return menuRepository.findAllByPriceBetween(minPrice, maxPrice);
        }
        return menuRepository.findAllByNameLikeIgnoreCaseAndPriceBetween('%' + name + '%', minPrice, maxPrice);
    }

    @Override
    public Menu updateMenu(Menu menu) {
        findByIdOrThrowException(menu.getId());
        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(String id) {
        findByIdOrThrowException(id);
        menuRepository.deleteById(id);
    }

    private Menu findByIdOrThrowException(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
    }
}
