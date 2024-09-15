package com.enigmacamp.warung_makan_bahari_api.repository;

import java.util.List;

import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TablesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.enigmacamp.warung_makan_bahari_api.entity.Tables;

public interface TablesRepository extends JpaRepository<Tables, String> {
    Page<Tables> getByNameLikeAllIgnoreCase(String name, Pageable pageable);
}
