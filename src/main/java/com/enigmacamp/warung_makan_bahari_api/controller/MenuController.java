package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public Menu addMenu(@Valid @RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @GetMapping
    public List<Menu> getAllMenu(@RequestParam (required = false) String name,
                                 @RequestParam (required = false) Long minPrice,
                                 @RequestParam (required = false) Long maxPrice) {
        return menuService.getAllMenu(name, minPrice, maxPrice);
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable String id) {
        return menuService.getMenuById(id);
    }

    @PutMapping()
    public Menu editMenu(@Valid @RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
    }
}
