package com.enigmacamp.warung_makan_bahari_api.service.impl;

import java.util.List;

import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.TablesRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TablesResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Tables;
import com.enigmacamp.warung_makan_bahari_api.repository.TablesRepository;
import com.enigmacamp.warung_makan_bahari_api.service.TablesService;

import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import jakarta.persistence.Table;
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
public class TablesServiceImpl implements TablesService {
    private final TablesRepository tablesRepository;
    private final ValidationUtil validationUtil;

    public Page<TablesResponse> getAllTables(String name, PagingRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        if (name == null) {
            Page<Tables> tables = tablesRepository.findAll(pageable);
            return tables.map(this::mapToResponse);
        }
        Page<Tables> tables = tablesRepository.getByNameLikeAllIgnoreCase('%' + name + '%', pageable);
        return tables.map(this::mapToResponse);
    }

    public TablesResponse getTablesById(String id) {
        return mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public Tables getById(String id) {
        return findByIdOrThrowException(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public TablesResponse addNewTables(TablesRequest tables) {
        validationUtil.validate(tables);
        Tables newTable = Tables.builder()
                .name(tables.getName())
                .build();
        tablesRepository.saveAndFlush(newTable);
        return mapToResponse(newTable);
    }

    @Transactional(rollbackFor = Exception.class)
    public TablesResponse editTables(String id, TablesRequest tables) {
        try {
            validationUtil.validate(tables);
            Tables currentTables = findByIdOrThrowException(id);
            currentTables.setName(tables.getName());
            tablesRepository.saveAndFlush(currentTables);
            return mapToResponse(currentTables);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input type");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTable(String id) {
        findByIdOrThrowException(id);
        tablesRepository.deleteById(id);
    }

    private Tables findByIdOrThrowException(String id) {
        return tablesRepository.findById(id).orElseThrow(() -> new RuntimeException("Table not found"));
    }

    private TablesResponse mapToResponse(Tables tables) {
        return TablesResponse.builder()
                .name(tables.getName())
                .build();
    }
}
