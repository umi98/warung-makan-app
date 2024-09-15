package com.enigmacamp.warung_makan_bahari_api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.warung_makan_bahari_api.entity.Tables;
import com.enigmacamp.warung_makan_bahari_api.service.TablesService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tables")
public class TablesController {
    private final TablesService tablesService;

    @GetMapping
    public List<Tables> getAllTables(@RequestParam(required = false) String name) {
        return tablesService.getAllTables(name);
    }

    @GetMapping("/{id}")
    public Tables getTablesById(@PathVariable String id) {
        return tablesService.getTablesById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Tables addNewTables(@RequestBody Tables tables) {
        return tablesService.addNewTables(tables);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Tables editTables(@RequestBody Tables tables) {
        return tablesService.ediTables(tables);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTables(@PathVariable String id) {
        tablesService.deleteTable(id);
    }

}
