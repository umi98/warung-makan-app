package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.constant.PathApi;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.TablesRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TablesResponse;
import com.enigmacamp.warung_makan_bahari_api.util.ResponseBuilderUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.warung_makan_bahari_api.service.TablesService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathApi.TABLES)
public class TablesController {
    private final TablesService tablesService;
    private final ResponseBuilderUtil responseBuilderUtil;

    @GetMapping
    public ResponseEntity<?> getAllTables(@RequestParam(required = false) String name,
                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<TablesResponse> result = tablesService.getAllTables(name, request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(result.getTotalElements())
                .totalPage(result.getTotalPages())
                .build();
        return responseBuilderUtil.buildResponsePaging(pagingResponse, result, "Successfully retrieve all data", HttpStatus.OK);
    }

    @GetMapping(PathApi.ID)
    public ResponseEntity<?> getTablesById(@PathVariable String id) {
        TablesResponse response = tablesService.getTablesById(id);
        return responseBuilderUtil.buildResponse(response, "Successfully retrieve all data", HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewTables(@RequestBody TablesRequest tables) {
        TablesResponse response = tablesService.addNewTables(tables);
        return responseBuilderUtil.buildResponse(response, "Successfully add new table", HttpStatus.OK);
    }

    @PutMapping(PathApi.ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editTables(@PathVariable String id, @RequestBody TablesRequest tables) {
        TablesResponse response = tablesService.editTables(id, tables);
        return responseBuilderUtil.buildResponse(response, "Table edited", HttpStatus.OK);
    }

    @DeleteMapping(PathApi.ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTables(@PathVariable String id) {
        tablesService.deleteTable(id);
        return responseBuilderUtil.buildResponse("OK", "Table deleted", HttpStatus.OK);
    }

}
