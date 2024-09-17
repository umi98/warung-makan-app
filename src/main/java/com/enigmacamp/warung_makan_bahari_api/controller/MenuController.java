package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.constant.PathApi;
import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathApi.MENUS)
public class MenuController {
    private final MenuService menuService;
    private final ResponseBuilder responseBuilder;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMenu(@RequestParam String name,
                                     @RequestParam Long price,
                                     @RequestParam MultipartFile img) {
        MenuRequest request = MenuRequest.builder()
                .name(name)
                .price(price)
                .multipartFile(img).build();
        MenuResponse menuResponse = menuService.addMenu(request);
        return responseBuilder.buildResponse(menuResponse, "Successfully add new menu", HttpStatus.CREATED);
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
        return responseBuilder.buildResponsePaging(pagingResponse, result, "Successfully retrieve all items", HttpStatus.FOUND);
    }

    @GetMapping(PathApi.ID)
    public ResponseEntity<?> getMenuById(@PathVariable String id) {
        MenuResponse response = menuService.getMenuById(id);
        return responseBuilder.buildResponse(response, "Successfully retrieve data", HttpStatus.OK);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> downloadImage(@PathVariable String id) {
        Resource result = menuService.getMenuImageById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getFilename() + "\"")
                .body(result);
    }

    @PutMapping(PathApi.ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editMenu(@PathVariable String id, @RequestBody MenuRequest menu) {
        MenuResponse response = menuService.updateMenu(id, menu);
        return responseBuilder.buildResponse(response, "Menu edited", HttpStatus.OK);
    }

    @DeleteMapping(PathApi.ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        return responseBuilder.buildResponse("OK", "Menu deleted", HttpStatus.OK);
    }
}
