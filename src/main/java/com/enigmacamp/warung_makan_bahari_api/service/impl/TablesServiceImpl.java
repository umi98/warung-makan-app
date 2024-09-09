package com.enigmacamp.warung_makan_bahari_api.service.impl;

import java.util.List;

import com.enigmacamp.warung_makan_bahari_api.entity.Tables;
import com.enigmacamp.warung_makan_bahari_api.repository.TablesRepository;
import com.enigmacamp.warung_makan_bahari_api.service.TablesService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TablesServiceImpl implements TablesService {
    private final TablesRepository tablesRepository;

    public List<Tables> getAllTables(String name) {
        if (name == null) {
            return tablesRepository.findAll();
        }
        return tablesRepository.getByNameLikeAllIgnoreCase('%' + name + '%');
    }

    public Tables getTablesById(String id) {
        return findByIdOrThrowException(id);
    }

    public Tables addNewTables(Tables tables) {
        return tablesRepository.save(tables);
    }

    public Tables ediTables(Tables tables) {
        findByIdOrThrowException(tables.getId());
        return tablesRepository.save(tables);
    }

    public void deleteTable(String id) {
        findByIdOrThrowException(id);
        tablesRepository.deleteById(id);
    }

    private Tables findByIdOrThrowException(String id) {
        return tablesRepository.findById(id).orElseThrow(() -> new RuntimeException("Table not found"));
    }
}
