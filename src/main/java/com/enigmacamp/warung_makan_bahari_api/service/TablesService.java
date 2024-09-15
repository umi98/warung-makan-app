package com.enigmacamp.warung_makan_bahari_api.service;

import java.util.List;

import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.TablesRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TablesResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.enigmacamp.warung_makan_bahari_api.entity.Tables;

public interface TablesService {
    Page<TablesResponse> getAllTables(String name, PagingRequest request);

    TablesResponse getTablesById(String id);

    TablesResponse addNewTables(TablesRequest tables);

    TablesResponse editTables(String id, TablesRequest tables);

    void deleteTable(String id);
}
