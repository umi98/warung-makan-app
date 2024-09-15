package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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
        Page<MenuResponse> result = menuService.getAllMenu(name, minPrice, maxPrice, request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(result.getTotalElements())
                .totalPage(result.getTotalPages())
                .build();
        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
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
    public ResponseEntity<?> getMenuById(@PathVariable String id) {
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .message("Successfully retrieve data")
                .statusCode(HttpStatus.OK.value())
                .data(menuService.getMenuById(id))
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editMenu(String id, @RequestBody MenuRequest menu) {
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .message("Menu edited")
                .statusCode(HttpStatus.OK.value())
                .data(menuService.updateMenu(id, menu))
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("Data deleted")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
