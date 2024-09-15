package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse addMenu(MenuRequest request) {
        validationUtil.validate(request);
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        menuRepository.saveAndFlush(menu);
        return mapToResponse(menu);
    }

    @Override
    public MenuResponse getMenuById(String id) {
        return mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public Menu getById(String id) {
        return findByIdOrThrowException(id);
    }

    @Override
    public Page<MenuResponse> getAllMenu(String name, Long minPrice, Long maxPrice, PagingRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<Menu> menus = menuRepository.findAll(pageable);
//        if (name == null && minPrice == null && maxPrice == null) {
//            result = menuRepository.findAll(pageable);
//        }
//        else if (name == null) {
//            result = menuRepository.findAllByPriceBetween(minPrice, maxPrice);
//        } else {
//            result = menuRepository.findAllByNameLikeIgnoreCaseAndPriceBetween('%' + name + '%', minPrice, maxPrice);
//        }
//        return result.stream().map(this::mapToResponse).toList();
        return menus.map(this::mapToResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse updateMenu(String id, MenuRequest menu) {
        try {
            validationUtil.validate(menu);
            Menu currentMenu = findByIdOrThrowException(id);
            currentMenu.setName(menu.getName());
            currentMenu.setPrice(menu.getPrice());
            menuRepository.save(currentMenu);
            return mapToResponse(currentMenu);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid type for price");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(String id) {
        findByIdOrThrowException(id);
        menuRepository.deleteById(id);
    }

    private Menu findByIdOrThrowException(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Menu not found"));
    }

    private MenuResponse mapToResponse(Menu menu) {
        return MenuResponse.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }
}
