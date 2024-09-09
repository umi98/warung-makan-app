package com.enigmacamp.warung_makan_bahari_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enigmacamp.warung_makan_bahari_api.entity.Tables;

public interface TablesService {
    List<Tables> getAllTables(String name);

    Tables getTablesById(String id);

    Tables addNewTables(Tables tables);

    Tables ediTables(Tables tables);

    void deleteTable(String id);
}
