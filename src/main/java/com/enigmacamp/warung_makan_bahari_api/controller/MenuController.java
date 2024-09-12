package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<?> addMenu(@RequestBody MenuRequest menuRequest) {
        MenuResponse menuResponse = menuService.addMenu(menuRequest);
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .message("Successfully add new menu")
                .statusCode(HttpStatus.CREATED.value())
                .data(menuResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllMenu(@RequestParam (required = false) String name,
                                        @RequestParam (required = false) Long minPrice,
                                        @RequestParam (required = false) Long maxPrice,
                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<Menu> result = menuService.getAllMenu(name, minPrice, maxPrice, request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(result.getTotalElements())
                .totalPage(result.getTotalPages())
                .build();
        CommonResponse<List<Menu>> response = CommonResponse.<List<Menu>>builder()
                .message("Successfully retrieve all items")
                .statusCode(HttpStatus.FOUND.value())
                .data(result.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
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
